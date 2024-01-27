package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentScheduleRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentSchedule
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.CalculationUtils
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.LoanPaymentScheduleUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanRepaymentFrequencies
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import javax.inject.Inject

@HiltViewModel
class SetupLoanForApprovalScreenViewModel @Inject constructor(
    private val loanTransactionRepository: LoanTransactionRepository?,
    private val paymentScheduleRepository: PaymentScheduleRepository?,
): ViewModel() {

    // Note: Long types represents the cent value of a money
    private var _totalPayment = MutableStateFlow(0L)
    val totalPayment: StateFlow<Long> = _totalPayment

    private var _principal = MutableStateFlow(0L)
    val principal: StateFlow<Long> = _principal

    private var _totalInterestAmount = MutableStateFlow(0L)
    val totalInterestAmount: StateFlow<Long> = _totalInterestAmount

    private var _amountPerPayment = MutableStateFlow(0L)
    val amountPerPayment: StateFlow<Long> = _amountPerPayment

    private var _numberOfPayments = MutableStateFlow(0)
    val numberOfPayments: StateFlow<Int> = _numberOfPayments

    var startDate: String = ""
    var dueDate: String = ""
    var interestRatePerc: Int = 0
    var frequencyPaymentMode: LoanRepaymentFrequencies = LoanRepaymentFrequencies.MONTHLY

    fun calculateTotalPaymentAsAnnual(
        principalAmount: Money,
        interestRatePercent: Int,
        timeInYears: Double,
   ) {
        _totalPayment.value =
            principalAmount.centValue + CalculationUtils.calculateSimpleInterestAnnual(principalAmount, interestRatePercent, timeInYears).centValue
    }

    fun calculateTotalPaymentAsMonthly(
        principalAmount: Money,
        interestRatePercent: Int,
        months: Int,
    ) {
        _totalPayment.value =
            principalAmount.centValue + CalculationUtils.calculateSimpleInterestMonthly(principalAmount, interestRatePercent, months).centValue
    }

    fun resetMoneyStuffFields() {
        _totalPayment.value = 0L
        _totalInterestAmount.value = 0
        _numberOfPayments.value = 0
    }

    fun calculateNumberOfPayments(
        months: Int,
        frequency: LoanRepaymentFrequencies
    ) {
        val weeksPerMonth = 4
        val numberOfPaymentsPerMonth =
            when (frequency) {
                LoanRepaymentFrequencies.MONTHLY -> 4
                LoanRepaymentFrequencies.SEMI_MONTHLY -> 2
                LoanRepaymentFrequencies.WEEKLY -> 1
            }

        _numberOfPayments.value =  months.times(weeksPerMonth).div(numberOfPaymentsPerMonth)
    }

    fun calculateInterestAmount(
        principalAmount: Money,
        interestRatePercent: Int,
        months: Int,
    ) {
        val a = CalculationUtils.calculateSimpleInterestMonthly(principalAmount, interestRatePercent, months)
        _totalInterestAmount.value = a.centValue
    }

    fun calculateAll(
        principalAmount: Money,
        interestRatePercent: Int,
        months: Int,
        frequency: LoanRepaymentFrequencies,
    ) {
        calculateTotalPaymentAsMonthly(principalAmount, interestRatePercent, months)
        calculateNumberOfPayments(months, frequency)
        calculateInterestAmount(principalAmount, interestRatePercent, months)
        _principal.value = principalAmount.centValue
        interestRatePerc = interestRatePercent
        frequencyPaymentMode = frequency
        _amountPerPayment.value = _totalPayment.value / _numberOfPayments.value
    }

    private var _retrievedLoanTransactionFlow = MutableStateFlow<Resource<LoanTransaction>?>(null)
    val retrievedLoanTransactionFlow:  StateFlow<Resource<LoanTransaction>?> = _retrievedLoanTransactionFlow

    private var _updatedLoanTransactionFlow = MutableStateFlow<Resource<LoanTransaction>?>(null)
    val updatedLoanTransactionFlow:  StateFlow<Resource<LoanTransaction>?> = _updatedLoanTransactionFlow

    private var _paymentScheduleDatesGenerationFlow = MutableStateFlow<Resource<PaymentSchedule>?>(null)
    val paymentScheduleDatesGenerationFlow:  StateFlow<Resource<PaymentSchedule>?> = _paymentScheduleDatesGenerationFlow

    fun declineLoanTransaction(transactionId: String) = viewModelScope.launch {
        _updatedLoanTransactionFlow.value = Resource.Loading
        val result = loanTransactionRepository?.declineLoanTransaction(transactionId)
        _updatedLoanTransactionFlow.value = result
    }

    fun approveLoanTransaction(loanTransactionItem: LoanTransaction) = viewModelScope.launch {
        val updatedLoanTransaction = loanTransactionItem
        updatedLoanTransaction.totalBalance = totalPayment.value
        updatedLoanTransaction.totalInterestBalance = totalInterestAmount.value

        updatedLoanTransaction.totalPayment = totalPayment.value
        updatedLoanTransaction.paymentPerSchedule = amountPerPayment.value
        updatedLoanTransaction.principalAmount = principal.value
        updatedLoanTransaction.interestRateInPercentage = interestRatePerc
        updatedLoanTransaction.startDate = DateTimeUtils.convertLocalDateToTimestamp(DateTimeUtils.parseISO8601DateString(startDate))
        updatedLoanTransaction.endDate = DateTimeUtils.convertLocalDateToTimestamp(DateTimeUtils.parseISO8601DateString(dueDate))
        updatedLoanTransaction.status = LoanTransactionStatus.APPROVED.name

        updatedLoanTransaction.repaymentFrequency = frequencyPaymentMode.name

        _updatedLoanTransactionFlow.value = Resource.Loading
        val result = loanTransactionRepository?.updateLoanTransaction(loanTransactionItem.id, updatedLoanTransaction)
        _updatedLoanTransactionFlow.value = result
    }

    fun generatePaymentScheduleForApprovedLoan(loanTransaction: LoanTransaction) = viewModelScope.launch {
        _paymentScheduleDatesGenerationFlow.value = Resource.Loading

        val result = paymentScheduleRepository?.addPaymentSchedule(
            PaymentSchedule(
                loanTransactionId = loanTransaction.id,
                paymentDates =
                    LoanPaymentScheduleUtils.generateDateSchedules(
                        loanTransaction.startDate!!.toDate(),
                        frequencyPaymentMode,
                        numberOfPayments.value,
                    ).map {
                        PaymentScheduleDate(
                            date = it,
                        )
                    }
            )
        )

        _paymentScheduleDatesGenerationFlow.value = result
    }
}