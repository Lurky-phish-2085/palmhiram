package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.data.models.UserCredentials
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.BriefUserProfileInformation
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ContentSection
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.DrawerTopBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.MenuButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import kotlin.math.log

@ExperimentalMaterial3Api
@Composable
fun DashboardSideProfileScreen(
    viewModel: AuthViewModel?,
    userDetails: User?,
    onCloseClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onQuickHelpClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DrawerTopBar(
                text = "Account",
                onClose = onCloseClick
            )
        },
        modifier = modifier
    ) { padding ->
        DashboardSideProfileScreenContent(
            userDetails = userDetails,
            onLogOutClick = onLogOutClick,
            onSettingsClick = onSettingsClick,
            onQuickHelpClick = onQuickHelpClick,
            onAboutClick = onAboutClick,
            modifier = Modifier
                .padding(padding)
                .padding(top = 16.dp)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun DashboardSideProfileScreenContent(
    userDetails: User?,
    onLogOutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onQuickHelpClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        BriefUserProfileInformation(
            username = userDetails?.name!!,
            email = userDetails.email,
            phone = userDetails.phone,
        )
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
                    onclick = onLogOutClick,
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
fun DashboardSideProfileScreenPreview() {
    PalmHiramTheme {
        Surface {
            DashboardSideProfileScreen(
                viewModel = null,
                userDetails = userCredentialSample,
                onCloseClick = { /*TODO*/ },
                onLogOutClick = { /*TODO*/ },
                onSettingsClick = { /*TODO*/ },
                onQuickHelpClick = { /*TODO*/ },
                onAboutClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(all = 16.dp)
            )
        }
    }
}

private val userCredentialSample = User(
    name = "Juan De La Cruz",
    email = "juandelacruz@gmail.com",
    phone = "09988776655",
)