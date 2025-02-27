package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.common.CommonColors

@Composable
fun WideButton(
    text: String,
    onclick: () -> Unit,
    enabled: Boolean = true,
    colors: ButtonColors? = null,
    modifier: Modifier = Modifier,
) {
    WideButton(
        onclick = { onclick() },
        enabled = enabled,
        colors = colors,
        modifier = modifier,
    ) {
        Text(
            text = text.uppercase(),
            fontWeight = FontWeight.ExtraBold
        )
    }
}
@Composable
fun WideButton(
    onclick: () -> Unit,
    enabled: Boolean = true,
    colors: ButtonColors? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Button(
        onClick = { onclick() },
        enabled = enabled,
        shape = MaterialTheme.shapes.large,
        colors = colors ?: ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = CommonColors.DISABLED_BUTTON_ALPHA),
        ),
        modifier = modifier
            .heightIn(53.dp)
            .fillMaxWidth()
    ) {
        content()
    }
}

@Preview
@Composable
fun WideButtonPreview() {
    Row {
        WideButton(text = "CLICK ME",onclick = {})
    }
}