package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme


@Composable
fun CircularProgressLoadingIndicator(
    isOpen: Boolean,
    modifier: Modifier = Modifier
) {
    if (isOpen) {
        Dialog(onDismissRequest = {}) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun CircularProgressLoadingIndicatorPreview() {
    PalmHiramTheme {
        Surface {
            Box(Modifier.fillMaxSize()) {
                CircularProgressLoadingIndicator (
                    isOpen = true
                )
            }
        }
    }
}