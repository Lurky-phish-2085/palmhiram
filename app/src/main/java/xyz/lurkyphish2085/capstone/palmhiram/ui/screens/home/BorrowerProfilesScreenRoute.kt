package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel

@Composable
fun BorrowerProfilesScreenRoute(
    onClose: () -> Unit,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    val profiles = lenderDashboardViewModel.borrowerUserProfiles.collectAsState()

    BorrowerProfilesScreen(
        onClose = onClose,
        profiles = profiles.value
    )
}