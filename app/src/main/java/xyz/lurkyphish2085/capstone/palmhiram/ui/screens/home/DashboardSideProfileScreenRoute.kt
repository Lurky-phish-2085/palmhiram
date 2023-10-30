package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun DashboardSideProfileScreenRoute(
    onCloseClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onQuickHelpClick: () -> Unit,
    onAboutClick: () -> Unit,
    viewModel: AuthViewModel,
) {
    DashboardSideProfileScreen(
        viewModel = viewModel,
        userDetails = viewModel.userDetails,
        onCloseClick = onCloseClick,
        onLogOutClick = {
            viewModel.logout()
            onLogOutClick()
        },
        onSettingsClick = onSettingsClick,
        onQuickHelpClick = onQuickHelpClick,
        onAboutClick = onAboutClick,
        modifier = Modifier
            .padding(all = 16.dp)
    )
}
