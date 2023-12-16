package xyz.lurkyphish2085.capstone.palmhiram.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import javax.inject.Inject

@HiltViewModel
class FunniGlobalViewModel @Inject constructor(
    // repository here!
): ViewModel() {

    // This is used to keep data from an element that contains LoanTransaction instance
    var selectedLoanTransactionItem: LoanTransaction = LoanTransaction()
}