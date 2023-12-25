package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel

// TODO: Add viewmodel
@ExperimentalMaterial3Api
@Composable
fun VerificationRoute(
    globalState: FunniGlobalViewModel,
    viewModel: AuthViewModel,
    onSuccessVerification: () -> Unit
) {
    AccountVerificationScreen(
        globalState = globalState,
        viewModel = viewModel,
        onSuccessVerification = onSuccessVerification,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    )
}
