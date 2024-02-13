package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel

@Composable
fun SettingsRoute(
    onClose: () -> Unit,
    globalState: FunniGlobalViewModel,
    globalAuthViewModel: AuthViewModel,
) {
    SettingsScreen(
        onClose = onClose,
        globalState = globalState,
        globalAuthViewModel = globalAuthViewModel,
        modifier = Modifier
            .padding(all = 16.dp)
    )
}