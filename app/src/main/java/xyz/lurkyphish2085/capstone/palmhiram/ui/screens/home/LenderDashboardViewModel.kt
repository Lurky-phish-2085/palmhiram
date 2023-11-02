package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import javax.inject.Inject

@HiltViewModel
class LenderDashboardViewModel @Inject constructor(

): ViewModel() {

    val balanceName = "Total amount to collect"
    val leftButtonName = "Collect"
    val rightButtonName = "Give Loan"

    var _totalCollectBalance = MutableStateFlow<Money>(Money(0.00))
    val totalCollectBalance: StateFlow<Money> = _totalCollectBalance
}