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

    val allLoanTransactionsOrderedByStartDate: Flow<List<LoanTransaction>>
        get() = loanTransactionRepository?.transactionsOrderedByStartDateDesc!!

    val allLoanTransactionsOrderedByEndDate: Flow<List<LoanTransaction>>
        get() = loanTransactionRepository?.transactionsOrderedByEndDateAsc!!

    // the total balance the borrower has to pay (all approved transactions by the current user)
    private var _totalPayablesBalance = MutableStateFlow<Money>(Money(0.00))
    val totalPayablesBalance: StateFlow<Money> = _totalPayablesBalance

    // runs the function to keep our balance updated
    init {
        collectTotalPayableBalance()
    }

    private fun collectTotalPayableBalance() = viewModelScope.launch {
        var total = Money(0.00)

        allLoanTransactions.collectLatest { transactions ->
            total = Money(0.00)
            transactions.forEach { transaction ->
                val transactionOwnerId = transaction.borrowerId
                val transactionStatus = LoanTransactionStatus.valueOf(transaction.status.uppercase())
                val balanceInCentValue = transaction.totalBalance

                val isTransactionApproved = transactionStatus == LoanTransactionStatus.APPROVED
                val doesCurrentUserOwnsTransaction = transactionOwnerId == currentUser?.uid

                if (isTransactionApproved && doesCurrentUserOwnsTransaction) {
                    total += Money.parseActualValue(transaction.totalBalance)
                }
            }

            _totalPayablesBalance.value = total

            if (transactions.isEmpty()) {
                _totalPayablesBalance.value = Money(0.00)
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
                    val ownedByCurrentUser = transaction.borrowerId == currentUser?.uid
                    val isPendingForApproval =
                        LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_LENDER

                    ownedByCurrentUser && isPendingForApproval
                }

                _approvalLoanTransactions.value = approvalTransactions
            }
    }
    private fun collectOngoingLoanTransaction() = viewModelScope.launch {
        allLoanTransactionsOrderedByEndDate
            .collectLatest {
                val ongoingTransactions = it.filter { transaction ->
                    val ownedByCurrentUser = transaction.borrowerId == currentUser?.uid
                    val isApproved = LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.APPROVED

                    ownedByCurrentUser && isApproved
                }

                _ongoingLoanTransactions.value = ongoingTransactions
            }
    }
    private fun collectSettledLoanTransaction() = viewModelScope.launch {
        allLoanTransactions
            .collectLatest {
                val settledTransactions = it.filter { transaction ->
                    val ownedByCurrentUser = transaction.borrowerId == currentUser?.uid
                    val isSettled = LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.SETTLED

                    ownedByCurrentUser && isSettled
                }

                _settledLoanTransactions.value = settledTransactions
            }
    }
    private fun collectCancelledLoanTransaction() = viewModelScope.launch {
        allLoanTransactions
            .collectLatest {
                val cancelledTransactions = it.filter { transaction ->
                    val ownedByCurrentUser = transaction.borrowerId == currentUser?.uid
                    val isCancelled = LoanTransactionStatus.valueOf(transaction.status.uppercase()) == LoanTransactionStatus.CANCELLED

                    ownedByCurrentUser && isCancelled
                }

                _cancelledLoanTransactions.value = cancelledTransactions
            }
    }

    private val _submissionFlow = MutableStateFlow<Resource<LoanTransaction>?>(null)
    val submissionFlow: StateFlow<Resource<LoanTransaction>?> = _submissionFlow

    fun submitLoanRequest(amount: Money, remarks: String) = viewModelScope.launch {
        val amountInCents = amount.centValue
        val loanRequestDetails = LoanTransaction(
            borrowerId = currentUser?.uid!!,
            borrowerName = currentUser?.displayName!!,
            totalBalance = amountInCents,
            principalAmount = amountInCents,
            startDate = Timestamp.now(),
            endDate = Timestamp.now(),
            status = LoanTransactionStatus.PENDING_FOR_APPROVAL_BY_LENDER.toString(),
            remarks = remarks,
        )

        _submissionFlow.value = Resource.Loading
        val result = loanTransactionRepository?.addLoanTransaction(loanRequestDetails)
        _submissionFlow.value = result
    }
}