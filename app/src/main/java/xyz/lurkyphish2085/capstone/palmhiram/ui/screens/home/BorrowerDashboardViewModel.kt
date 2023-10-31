package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.lurkyphish2085.capstone.palmhiram.data.AuthRepository
import javax.inject.Inject

@HiltViewModel
class BorrowerDashboardViewModel @Inject constructor(
): ViewModel() {

    val balanceName = "Total amount to pay"
    val leftButtonName = "Apply Loan"
    val rightButtonName = "Pay Loan"
}