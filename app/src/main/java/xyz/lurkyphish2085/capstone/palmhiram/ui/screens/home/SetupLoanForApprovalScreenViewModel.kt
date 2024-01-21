package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.data.models.VerificationCode
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

    fun calculateTotalPayment(
        principalAmount: Money,
        interestRatePercent: Int,
        timeInYears: Double,
   ) {
        _totalPayment.value =
            principalAmount.centValue + CalculationUtils.calculateSimpleInterest(principalAmount, interestRatePercent, timeInYears).centValue
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