package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    // the total balance the lender has to collect (all approved transactions combined)
    var _totalCollectBalance = MutableStateFlow<Money>(Money(0.00))
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
}