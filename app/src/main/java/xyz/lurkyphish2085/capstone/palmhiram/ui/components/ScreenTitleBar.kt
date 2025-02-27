package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.common.CommonColors
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun ScreenTitleBar(
    text: String,
    enable: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Crossfade(targetState = text) {
            Text(
                text = it.uppercase(),
                style = MaterialTheme.typography.headlineLarge,
                color = if (!enable) {
                    MaterialTheme.colorScheme.onBackground.copy(alpha = CommonColors.DISABLED_COMPONENT_ALPHA)
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        }
    }
}

@Preview(widthDp = 512)
@Composable
fun ScreenTitleBarPreview() {
    PalmHiramTheme {
        Surface {
            ScreenTitleBar(text = "MAIN SCREEN")
        }
    }
}