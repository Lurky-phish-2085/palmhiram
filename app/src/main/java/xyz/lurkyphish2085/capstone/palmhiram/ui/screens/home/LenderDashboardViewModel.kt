package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.AuthRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import javax.inject.Inject

@HiltViewModel
class LenderDashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository?,
    private val loanTransactionRepository: LoanTransactionRepository?,
    // TODO: Add one for the notifications
): ViewModel() {

    val balanceName = "Total amount to collect"
    val leftButtonName = "Collect"
    val rightButtonName = "Give Loan"

    val currentUser: FirebaseUser?
        get() = authRepository?.currentUser

    val allLoanTransactions: Flow<List<LoanTransaction>>
        get() = loanTransactionRepository?.transactions!!

    val allLoanTransactionsOrderedByStartDate: Flow<List<LoanTransaction>>
        get() = loanTransactionRepository?.transactionsOrderedByStartDateDesc!!

    val allLoanTransactionsOrderedByEndDate: Flow<List<LoanTransaction>>
        get() = loanTransactionRepository?.transactionsOrderedByEndDateAsc!!

    // the total balance the lender has to collect (all approved transactions combined)
    private var _totalCollectBalance = MutableStateFlow<Money>(Money(0.00))
    val totalCollectBalance: StateFlow<Money> = _totalCollectBalance

    // runs the function to keep our balance updated
    init {
        collectTotalCollectableBalance()
    }

    private fun collectTotalCollectableBalance() = viewModelScope.launch {
        var total = Money(0.00)

        allLoanTransactions.collectLatest { transactions ->
            total = Money(0.00)
            transactions.forEach { transaction ->
                val transactionStatus = LoanTransactionStatus.valueOf(transaction.status.uppercase())
                val balanceInCentValue = transaction.totalBalance

                if (transactionStatus == LoanTransactionStatus.APPROVED) {
                    total += Money.parseActualValue(transaction.totalBalance)
                }
            }

            _totalCollectBalance.value = total

            if (transactions.isEmpty()) {
                _totalCollectBalance.value = Money(0.00)
            }
        }
    }

    private var _approvalLoanTransactions = MutableStateFlow<List<LoanTransaction>>(ArrayList<LoanTransaction>())
    private var _ongoingLoanTransactions = MutableStateFlow<List<LoanTransaction>>(ArrayList<LoanTransaction>())
    private var _settledLoanTransactions = MutableStateFlow<List<LoanTransaction>>(ArrayList<LoanTransaction>())
    private var _cancelledLoanTransactions = MutableStateFlow<List<LoanTransaction>>(ArrayList<LoanTransaction>())

    val approvalLoanTransactions: StateFlow<List<LoanTransaction>> = _approvalLoanTransactions
    val ongoingLoanTransactions: StateFlow<List<LoanTransaction>> = _ongoingLoanTransactions
    val settledLoanTransactions: StateFlow<List<LoanTransaction>> = _settledLoanTransactions
    val cancelledLoanTransactions: StateFlow<List<LoanTransaction>> = _cancelledLoanTransactions

    init {
        collectApprovalLoanTransaction()
        collectOngoingLoanTransaction()
        collectSettledLoanTransaction()
        collectCancelledLoanTransaction()
    }

    private fun collectApprovalLoanTransaction() = viewModelScope.launch {
        allLoanTransactionsOrderedByStartDate
            .collectLatest {
                val approvalTransactions = it.filter { transaction ->
                    (LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_BORROWER)
                            ||
                            (LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_LENDER)
                }

                _approvalLoanTransactions.value = approvalTransactions
            }
    }
    private fun collectOngoingLoanTransaction() = viewModelScope.launch {
        allLoanTransactionsOrderedByEndDate
            .collectLatest {
                val ongoingTransactions = it.filter { transaction ->
                    LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.APPROVED
                }

                _ongoingLoanTransactions.value = ongoingTransactions
            }
    }
    private fun collectSettledLoanTransaction() = viewModelScope.launch {
        allLoanTransactions
            .collectLatest {
                val settledTransactions = it.filter { transaction ->
                    LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.SETTLED
                }

                _settledLoanTransactions.value = settledTransactions
            }
    }
    private fun collectCancelledLoanTransaction() = viewModelScope.launch {
        allLoanTransactions
            .collectLatest {
                val cancelledTransactions = it.filter { transaction ->
                    LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.CANCELLED
                }

                _cancelledLoanTransactions.value = cancelledTransactions
            }
    }
}