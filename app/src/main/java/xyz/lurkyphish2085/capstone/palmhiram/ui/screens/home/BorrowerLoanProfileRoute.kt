package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel


@Composable
fun BorrowerLoanProfileRoute(
    onClose: () -> Unit,
    onItemClick: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: BorrowerLoanProfileViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.borrowerUser.value = globalState.selectedUserProfileItem
        viewModel.updateList(globalState.selectedUserProfileItem.userId)
    }

    BorrowerLoanProfileScreen(
        onClose = onClose,
        onItemClick = onItemClick,
        globalState = globalState,
        viewModel = viewModel,
        modifier = Modifier.padding(all = 16.dp)
    )
}