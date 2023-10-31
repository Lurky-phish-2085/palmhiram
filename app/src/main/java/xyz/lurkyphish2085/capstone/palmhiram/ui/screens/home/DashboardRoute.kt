package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel


@ExperimentalMaterial3Api
@Composable
fun DashboardRoute(
    onProfileClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    authViewModel: AuthViewModel,
    borrowerDashboardViewModel: BorrowerDashboardViewModel,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    DashboardScreen(
        onProfileClick = onProfileClick,
        onNotificationsClick = onNotificationsClick,
        authViewModel = authViewModel,
        borrowerDashboardViewModel = borrowerDashboardViewModel,
        lenderDashboardViewModel = lenderDashboardViewModel,
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    )
}
