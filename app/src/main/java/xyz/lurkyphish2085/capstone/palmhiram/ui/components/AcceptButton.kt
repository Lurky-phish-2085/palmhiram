package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AcceptButton(
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Button(
        onClick = {},
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .heightIn(53.dp)
            .fillMaxWidth()
    ) {
        content()
    }
}

@Preview
@Composable
fun AcceptButtonPreview() {
    Row {
        AcceptButton(onclick = {}) {
            Text(text = "BUTTON")
        }
    }
}