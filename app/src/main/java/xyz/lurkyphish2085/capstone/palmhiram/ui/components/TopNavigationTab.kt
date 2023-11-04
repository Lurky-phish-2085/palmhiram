package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun TopNavigationTab(
    onClick: () -> Unit,
    selected: Boolean = false,
    text: String,
    modifier: Modifier = Modifier
) {
    FilledTonalButton(
        onClick = onClick,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor =
                when (selected) {
                    true -> MaterialTheme.colorScheme.onBackground
                    else -> MaterialTheme.colorScheme.background
                },
            contentColor =
               when (selected) {
                   true -> MaterialTheme.colorScheme.onPrimary
                   else -> MaterialTheme.colorScheme.outlineVariant
               }
        ),
        modifier = modifier
            .heightIn(min = 28.dp)
    ) {
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

@Preview
@Composable
fun TopNavigationTabPreview() {
    PalmHiramTheme {
        Surface {
            var selected by remember {
                mutableStateOf(false)
            }
            TopNavigationTab(
                onClick = { selected = !selected },
                selected = selected,
                text = "Home"
            )
        }
    }
}