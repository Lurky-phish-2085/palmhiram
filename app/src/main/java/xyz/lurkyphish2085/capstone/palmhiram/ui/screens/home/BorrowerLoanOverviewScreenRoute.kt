package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun BorrowerLoanOverviewScreenRoute(
    onClose: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: BorrowerLoanOverviewViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.selectedLoanTransaction = globalState.selectedLoanTransactionItem
    }

    BorrowerLoanOverviewScreen(
        onClose = onClose,
        globalState = globalState,
        viewModel = viewModel,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    )
}
