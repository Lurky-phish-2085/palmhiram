package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import javax.inject.Inject

@HiltViewModel
class BorrowerLoanProfileViewModel @Inject constructor(
    private val repository: LoanTransactionRepository
): ViewModel() {

    val borrowerUser = MutableStateFlow<User?>(null)

    val transactions
        get() = repository.transactions

    val transactionsOrderedByEndDateAsc
        get() = repository.transactionsOrderedByEndDateAsc

    val transactionsOrderedByStartDateDesc
        get() = repository.transactionsOrderedByStartDateDesc

    val borrowerPendingLoan = MutableStateFlow<List<LoanTransaction>?>(null)
    val borrowerSettledLoans = MutableStateFlow<List<LoanTransaction>?>(null)
    val borrowerCancelledLoans = MutableStateFlow<List<LoanTransaction>?>(null)

    fun updateList(userId: String) = viewModelScope.launch {
        collectPendingLoans(userId)
        collectSettledLoans(userId)
        collectCancelledLoans(userId)
    }

    fun collectPendingLoans(userId: String) = viewModelScope.launch {
        transactionsOrderedByEndDateAsc
            .collect {
                val list = it.filter {
                    (it.borrowerId == userId) && (it.status == LoanTransactionStatus.APPROVED.name)
                }

                borrowerPendingLoan.value = list
            }
    }
    fun collectSettledLoans(userId: String) = viewModelScope.launch {
        transactionsOrderedByEndDateAsc
            .collect {
                val list = it.filter {
                    (it.borrowerId == userId) && (it.status == LoanTransactionStatus.SETTLED.name)
                }

                borrowerSettledLoans.value = list
            }
    }
    fun collectCancelledLoans(userId: String) = viewModelScope.launch {
        transactionsOrderedByEndDateAsc
            .collect {
                val list = it.filter {
                    (it.borrowerId == userId) && (it.status == LoanTransactionStatus.CANCELLED.name)
                }

                borrowerCancelledLoans.value = list
            }
    }
}