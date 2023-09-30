package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun ClickableTextWithLabel(
    label: String,
    text: String,
    onClick: (Int) -> Unit,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
        )

        ClickableText(
            text = AnnotatedString(
                text = text,
                spanStyle = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                )
            ),
            onClick = {
                if (isEnabled) onClick(it)
            }
        )
    }
}

@Preview
@Composable
fun ClickableTextWithLabelPreview() {
    PalmHiramTheme {
        Surface {
            var enable by remember {
                mutableStateOf(true)
            }

            Column {
                ClickableTextWithLabel(
                    label = "New here?",
                    text = "Sign up here",
                    isEnabled = enable,
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { enable = !enable }) { Text(text = if (enable) "Disable" else "Enable") }
            }
        }
    }
}