package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// TODO: Add viewmodel
@ExperimentalMaterial3Api
@Composable
fun VerificationRoute(
    onSubmit: () -> Unit
) {
    AccountVerificationScreen(
        onSubmit = onSubmit,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    )
}
