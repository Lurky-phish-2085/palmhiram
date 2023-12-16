package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel


@ExperimentalMaterial3Api
@Composable
fun DashboardRoute(
    globalState: FunniGlobalViewModel,
    onProfileClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onBorrowerApplyLoanClick: () -> Unit,
    onBorrowerPayLoanClick: () -> Unit,
    onLenderCollectLoanClick: () -> Unit,
    onLenderGiveLoanClick: () -> Unit,
    onLoansClickAsLender: () -> Unit,
    onLoansClickAsBorrower: () -> Unit,
    onSelectedLoanTransactionAsLender: () -> Unit,
    onSelectedLoanTransactionAsBorrower: () -> Unit,
    authViewModel: AuthViewModel,
    borrowerDashboardViewModel: BorrowerDashboardViewModel,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    DashboardScreen(
        globalState = globalState,
        onProfileClick = onProfileClick,
        onNotificationsClick = onNotificationsClick,
        authViewModel = authViewModel,
        borrowerDashboardViewModel = borrowerDashboardViewModel,
        lenderDashboardViewModel = lenderDashboardViewModel,
        onLeftButtonClickAsLender = onLenderCollectLoanClick,
        onRightButtonClickAsLender = onLenderGiveLoanClick,
        onLeftButtonClickAsBorrower = onBorrowerApplyLoanClick,
        onRightButtonClickAsBorrower = onBorrowerPayLoanClick,
        onLoansClickAsLender = onLoansClickAsLender,
        onLoansClickAsBorrower = onLoansClickAsBorrower,
        onSelectedLoanTransactionItemAsLender = onSelectedLoanTransactionAsLender,
        onSelectedLoanTransactionItemAsBorrower = onSelectedLoanTransactionAsBorrower,
        modifier = Modifier
    )
}
