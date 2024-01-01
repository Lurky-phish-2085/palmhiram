package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SetupLoanApprovalScreenRoute(
    onClose: () -> Unit,
    globalState: FunniGlobalViewModel,
    setupLoanForApprovalScreenViewModel: SetupLoanForApprovalScreenViewModel,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    SetupLoanForApprovalScreen(
        globalState = globalState,
        balanceName = lenderDashboardViewModel.balanceName,
        viewModel = setupLoanForApprovalScreenViewModel,
        lenderDashboardViewModel = lenderDashboardViewModel,
        onClose = onClose,
        transactionDetails = globalState.selectedLoanTransactionItem,
        modifier = Modifier.padding(all = 16.dp)
    )
}