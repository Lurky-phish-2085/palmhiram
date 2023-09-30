package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorText(
    errorText: String = "Not a valid value",
    modifier: Modifier = Modifier
) {
    Text(
        text = errorText,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier
    )
}