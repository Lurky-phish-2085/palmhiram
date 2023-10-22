package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ScreenTitleBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    Spacer(modifier = Modifier.weight(1f, true))
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(onClick = { navController.navigate("overview") },
                        Modifier
                            .weight(1f)
                            .widthIn(min = ButtonDefaults.MinWidth)
                            .heightIn(min = ButtonDefaults.MinHeight)
                    ) {
                        Text(text = "Home")
                    }
                    OutlinedButton(onClick = { navController.navigate("a") },
                        Modifier
                            .weight(1f)
                            .widthIn(min = ButtonDefaults.MinWidth)
                            .heightIn(min = ButtonDefaults.MinHeight)
                    ) {
                        Text(text = "Calendar")
                    }
                    OutlinedButton(onClick = { navController.navigate("b") },
                        Modifier
                            .weight(1f)
                            .widthIn(min = ButtonDefaults.MinWidth)
                            .heightIn(min = ButtonDefaults.MinHeight)
                    ) {
                        Text(text = "Reports")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        modifier = modifier
    ) { padding ->
        HomeNavigation(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}


@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenPreview() {
    PalmHiramTheme {
        Surface {
            DashboardScreen(
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}
