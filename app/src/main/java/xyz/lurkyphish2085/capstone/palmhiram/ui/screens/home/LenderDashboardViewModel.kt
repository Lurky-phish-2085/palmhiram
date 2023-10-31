package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LenderDashboardViewModel @Inject constructor(

): ViewModel() {

    val balanceName = "Total amount to collect"
    val leftButtonName = "Collect"
    val rightButtonName = "Give Loan"
}