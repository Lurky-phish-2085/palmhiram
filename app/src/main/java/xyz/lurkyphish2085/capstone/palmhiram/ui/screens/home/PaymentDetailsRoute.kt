package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel


@Composable
fun PaymentDetailsRoute(
    globalState: FunniGlobalViewModel,
    onClose: () -> Unit,
    viewModel: BorrowerConfirmLoanPaymentViewModel,
) {
    PaymentDetailsScreen(
        globalState, onClose, viewModel,
        modifier = Modifier.padding(16.dp)
    )
}