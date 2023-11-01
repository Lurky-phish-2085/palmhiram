package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun ApplyLoanRoute(
    viewModel: BorrowerDashboardViewModel,
    onSubmit: () -> Unit,
    onCloseClick: () -> Unit,
) {
    ApplyLoanScreen(
        viewModel = viewModel,
        onSubmit = onSubmit,
        onClose = onCloseClick,
        modifier = Modifier.padding(all = 16.dp)
    )
}
