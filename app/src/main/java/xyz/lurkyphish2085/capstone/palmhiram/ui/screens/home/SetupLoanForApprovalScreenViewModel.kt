package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.CalculationUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import javax.inject.Inject

@HiltViewModel
class SetupLoanForApprovalScreenViewModel @Inject constructor(
    private val loanTransactionRepository: LoanTransactionRepository?,
): ViewModel() {

    // Note: Long types represents the cent value of a money
    private var _totalPayment = MutableStateFlow(0L)
    val totalPayment: StateFlow<Long> = _totalPayment

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

    fun resetTotalPayment() {
        _totalPayment.value = 0L
    }

    private var _retrievedLoanTransactionFlow = MutableStateFlow<Resource<LoanTransaction>?>(null)
    val retrievedLoanTransactionFlow:  StateFlow<Resource<LoanTransaction>?> = _retrievedLoanTransactionFlow

    private var _updatedLoanTransactionFlow = MutableStateFlow<Resource<LoanTransaction>?>(null)
    val updatedLoanTransactionFlow:  StateFlow<Resource<LoanTransaction>?> = _updatedLoanTransactionFlow

    fun declineLoanTransaction(transactionId: String) = viewModelScope.launch {
        _updatedLoanTransactionFlow.value = Resource.Loading
        val result = loanTransactionRepository?.declineLoanTransaction(transactionId)
        _updatedLoanTransactionFlow.value = result
    }
}