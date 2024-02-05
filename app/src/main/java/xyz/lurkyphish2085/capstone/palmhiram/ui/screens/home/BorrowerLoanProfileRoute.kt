package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel


@Composable
fun BorrowerLoanProfileRoute(
    onClose: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: BorrowerLoanProfileViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.updateList(globalState.selectedUserProfileItem.userId)
    }

    BorrowerLoanProfileScreen(
        onClose = onClose,
        globalState = globalState,
        viewModel = viewModel
    )
}