package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentScheduleRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentSchedulesRepositoryImpl
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.data.models.Payment
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentSchedule
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import javax.inject.Inject


@HiltViewModel
class BorrowerLoanOverviewViewModel @Inject constructor(
    private val loanTransactionRepository: LoanTransactionRepository?,
    private val paymentScheduleRepository: PaymentScheduleRepository?,
    private val paymentRepository: PaymentRepository,
): ViewModel() {

    var selectedLoanTransaction = LoanTransaction()

    val paymentSchedules: Flow<List<PaymentSchedule>>
        get() = paymentScheduleRepository?.paymentSchedules!!

    val paymentDates: Flow<List<PaymentScheduleDate>>
        get() = paymentScheduleRepository?.paymentDates!!

    val payments: Flow<List<Payment>>
        get() = paymentRepository.payments

    private var _paymentSchedulesOfSelectedTransaction = MutableStateFlow<List<PaymentSchedule>>(
        listOf(
            PaymentSchedule()
        )
    )
    val paymentSchedulesOfSelectedTransaction: StateFlow<List<PaymentSchedule>> =
        _paymentSchedulesOfSelectedTransaction

    private var _paymentSchedulesDatesOfSelectedTransaction =
        MutableStateFlow<List<PaymentScheduleDate>>(listOf(PaymentScheduleDate()))
    val paymentSchedulesDatesOfSelectedTransaction: StateFlow<List<PaymentScheduleDate>> =
        _paymentSchedulesDatesOfSelectedTransaction

    var paymentForSelectedDate: Payment = Payment()

    fun collectPaymentScheduleOfLoan(selectedLoanTransactionId: String) = viewModelScope.launch {
        paymentSchedules
            .collectLatest { schedules ->
                val collectedSchedules = schedules.filter { schedule ->
                    schedule.loanTransactionId == selectedLoanTransactionId
                }

                _paymentSchedulesOfSelectedTransaction.value = collectedSchedules
            }
    }

    private fun collectPaymentScheduleDatesOfLoan() = viewModelScope.launch {
        paymentDates.collectLatest {
            val collectedValues = it.filter {
                it.loanTransactionId == selectedLoanTransaction.id
            }

            _paymentSchedulesDatesOfSelectedTransaction.value = collectedValues
        }
    }

    init {
        collectPaymentScheduleOfLoan(selectedLoanTransactionId = selectedLoanTransaction.id)
        collectPaymentScheduleDatesOfLoan()
    }

    fun getPaymentForSelectedPaymentDate(paymentDate: PaymentScheduleDate) = viewModelScope.launch {
        payments.collectLatest {
            val filteredPayment = it.filter {  payment ->
                payment.date == paymentDate.date
            }.get(0)

            paymentForSelectedDate = filteredPayment
        }
    }
}