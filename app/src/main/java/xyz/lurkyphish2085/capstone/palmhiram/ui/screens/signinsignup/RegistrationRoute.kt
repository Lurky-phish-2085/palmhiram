package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// TODO: Add viewmodel
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@ExperimentalMaterial3Api
@Composable
fun RegistrationRoute(
    viewModel: AuthViewModel,
    onSubmit: () -> Unit
) {
    RegistrationScreen(
        viewModel = viewModel,
        onSubmit = onSubmit,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    )
}
