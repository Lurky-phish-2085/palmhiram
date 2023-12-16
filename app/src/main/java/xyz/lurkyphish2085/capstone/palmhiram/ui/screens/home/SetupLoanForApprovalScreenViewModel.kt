package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.CalculationUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import javax.inject.Inject

@HiltViewModel
class SetupLoanForApprovalScreenViewModel @Inject constructor(
    // repository here!
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
}