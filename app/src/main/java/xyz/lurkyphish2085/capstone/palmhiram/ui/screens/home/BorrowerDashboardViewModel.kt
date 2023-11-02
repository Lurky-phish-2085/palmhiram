package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.AuthRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import javax.inject.Inject

@HiltViewModel
class BorrowerDashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository?,
    private val loanTransactionRepository: LoanTransactionRepository?,
    // TODO: Add one for the notifications
): ViewModel() {

    val balanceName = "Total amount to pay"
    val leftButtonName = "Apply Loan"
    val rightButtonName = "Pay Loan"

    val currentUser: FirebaseUser?
        get() = authRepository?.currentUser

    val allLoanTransactions: Flow<List<LoanTransaction>>
        get() = loanTransactionRepository?.transactions!!

    // the total balance the borrower has to pay (all approved transactions combined)
    var _totalPayablesBalance = MutableStateFlow<Money>(Money(0.00))
    val totalPayablesBalanc: StateFlow<Money> = _totalPayablesBalance

    // runs the function to keep our balance updated
    init {
        collectTotalPayableBalance()
    }

    private fun collectTotalPayableBalance() = viewModelScope.launch {
        var total = Money(0.00)

        allLoanTransactions.collectLatest { transactions ->
            total = Money(0.00)
            transactions.forEach { transaction ->
                val transactionStatus = LoanTransactionStatus.valueOf(transaction.status.uppercase())
                val balanceInCentValue = transaction.totalBalance

                if (transactionStatus == LoanTransactionStatus.APPROVED) {
                    total += Money.actualValueOf(transaction.totalBalance)
                }
            }

            _totalPayablesBalance.value = total

            if (transactions.isEmpty()) {
                _totalPayablesBalance.value = Money(0.00)
            }

        }
    }

    private val _submissionFlow = MutableStateFlow<Resource<LoanTransaction>?>(null)
    val submissionFlow: StateFlow<Resource<LoanTransaction>?> = _submissionFlow

    fun submitLoanRequest(amount: Money) = viewModelScope.launch {
        val amountInCents = amount.centValue
        val loanRequestDetails = LoanTransaction(
            borrowerId = currentUser?.uid!!,
            principalAmount = amountInCents,
            startDate = Timestamp.now(),
            status = LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_LENDER.toString(),
        )

        _submissionFlow.value = Resource.Loading
        val result = loanTransactionRepository?.addLoanTransaction(loanRequestDetails)
        _submissionFlow.value = result
    }
}