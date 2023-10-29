package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun DrawerTopBar(
    text: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.weight(1f, true))
        IconButton(
            onClick = onClose
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "close",
                modifier = Modifier
                    .size(36.dp)
            )
        }
    }
}

@Preview
@Composable
fun DrawerTopBarPreview() {
    PalmHiramTheme {
        Surface {
            DrawerTopBar(
                text = "Account",
                onClose = {},
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}