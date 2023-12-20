package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun BorrowerProfilesScreenRoute(
    onClose: () -> Unit,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    val profilesFlow = lenderDashboardViewModel.borrowerUserProfiles.collectAsState()

    BorrowerProfilesScreen(
        onClose = onClose,
        profiles = profilesFlow
    )

    Log.e("BORROWER LIST DEBUG", "BorrowerProfilesScreenRoute: ${profilesFlow.value}", )
}