package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun BriefUserProfileInformation(
    username: String,
    email: String,
    phone: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier
                .size(64.dp)
        )

        Text(
            text = username,
            style = MaterialTheme.typography.displaySmall
                .copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = email,
            style = MaterialTheme.typography.bodyLarge
                .copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.outline
                )
        )
        Text(
            text = phone,
            style = MaterialTheme.typography.bodyLarge
                .copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.outline
                )
        )
    }
}

@Preview
@Composable
fun BriefUserProfileInformationPreview() {
    PalmHiramTheme {
        Surface {
            BriefUserProfileInformation(
                username = "Juan De La Cruz",
                email = "juandelacruz@gmail.com",
                phone = "09988776655"
            )
        }
    }
}