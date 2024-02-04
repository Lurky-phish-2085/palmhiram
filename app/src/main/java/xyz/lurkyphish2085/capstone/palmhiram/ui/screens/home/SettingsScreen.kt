package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel


@Composable
fun SettingsScreen(
    onClose: () -> Unit,
    globalState: FunniGlobalViewModel,
    modifier: Modifier
) {
    Column(modifier) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onClose, modifier = Modifier.align(Alignment.BottomStart)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Change Password", style = MaterialTheme.typography.headlineLarge)
        OutlinedTextField(
            label = { Text(text = "Old Password") },
            value = "",
            onValueChange = {}
        )
        OutlinedTextField(
            label = { Text(text = "New Password") },
            value = "",
            onValueChange = {}
        )
        Button(onClick = {}) {
            Text(text = "Apply password")
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}