package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BorrowerProfilesScreenRoute(
    onClose: () -> Unit,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    val profilesFlow = lenderDashboardViewModel.verifiedBorrowerUserProfiles.collectAsState()

    BorrowerProfilesScreen(
        onClose = onClose,
        onSearchValueChange = { queriedName -> lenderDashboardViewModel.collectVerifiedBorrowerUserProfiles(queriedName) },
        profiles = profilesFlow,
    )
}