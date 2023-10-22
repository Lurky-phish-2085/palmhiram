package xyz.lurkyphish2085.capstone.palmhiram.ui.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.lurkyphish2085.capstone.palmhiram.PalmHiramNavHost
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun PalmHiramApp() {
    @OptIn(ExperimentalMaterial3Api::class)
    PalmHiramTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            // TODO: Insert NavHost here
            PalmHiramNavHost()
        }
    }
}