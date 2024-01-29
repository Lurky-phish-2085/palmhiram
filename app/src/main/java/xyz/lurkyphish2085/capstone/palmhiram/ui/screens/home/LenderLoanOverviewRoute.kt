package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel

@Composable
fun LenderLoanOverviewRoute (
    onClose: () -> Unit,
    onSelectedUnderApprovalPaymentItemClick: () -> Unit,
    onSelectedNonUnderApprovalPaymentItemClick: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: LenderLoanOverviewViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.selectedLoanTransaction = globalState.selectedLoanTransactionItem
    }

    LenderLoanOverviewScreen(
        onClose = onClose,
        onSelectedUnderApprovalPaymentItemClick = onSelectedUnderApprovalPaymentItemClick,
        onSelectedNonUnderApprovalPaymentItemClick = onSelectedNonUnderApprovalPaymentItemClick,
        globalState = globalState,
        viewModel = viewModel,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    )
}