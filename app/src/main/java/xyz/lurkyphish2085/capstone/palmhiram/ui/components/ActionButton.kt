package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun ActionButton(
    icon: ImageVector,
    actionName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        FilledTonalIconButton(
            onClick = onClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.outlineVariant
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .size(64.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(48.dp))
        }

        Text(
            text = actionName,
            style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray, fontWeight = FontWeight.Black,)
        )
    }
}

@Preview
@Composable
fun ActionButtonPreview() {
    PalmHiramTheme {
        Surface {
            ActionButton(
                onClick = {},
                icon = Icons.Outlined.AddBox,
                actionName = "Add"
            )
        }
    }
}