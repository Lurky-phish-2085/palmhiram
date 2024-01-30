package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel


@Composable
fun LenderDeclineRoute(
    onClose: () -> Unit,
    onSubmit: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: LenderLoanOverviewViewModel,
) {
    LenderDeclineLoanScreen(
        onClose = onClose,
        onSubmit = onSubmit,
        globalState = globalState,
        viewModel = viewModel,
        modifier = Modifier
            .padding(all = 16.dp)
    )
}