package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ContentSection
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.DrawerTopBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.MenuButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@ExperimentalMaterial3Api
@Composable
fun DashboardSideProfileScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DrawerTopBar(text = "Acccount", onClose = { /*TODO*/ })
        },
        modifier = modifier
    ) { padding ->
        DashboardSideProfileScreenContent(
            modifier = Modifier.padding(padding)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun DashboardSideProfileScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ContentSection {
            Column() {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profile Menu",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                MenuButton(
                    menuName = "Settings",
                    icon = Icons.Outlined.Settings,
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                MenuButton(
                    menuName = "Quick Help",
                    icon = Icons.Outlined.HelpOutline,
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                MenuButton(
                    menuName = "About",
                    icon = Icons.Outlined.Info,
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                WideButton(
                    text = "LOG OUT",
                    onclick = { /*TODO*/ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.outlineVariant,
                        contentColor = MaterialTheme.colorScheme.outline,
                    ),
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenSideMenuPreview() {
    PalmHiramTheme {
        Surface {
            DashboardSideProfileScreen(
                modifier = Modifier
                    .padding(all = 16.dp)
            )
        }
    }
}