package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Balance(
    amount: String,
    currencySymbol: Char,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
    ) {
        Text(
            text = "$currencySymbol $amount",
            style = MaterialTheme.typography.displayMedium
        )
    }
}

