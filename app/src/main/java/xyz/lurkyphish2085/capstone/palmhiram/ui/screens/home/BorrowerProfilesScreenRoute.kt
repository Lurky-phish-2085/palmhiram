package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel

@Composable
fun BorrowerProfilesScreenRoute(
    globalState: FunniGlobalViewModel,
    onClose: () -> Unit,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    val verifiedProfilesFlow = lenderDashboardViewModel.verifiedBorrowerUserProfiles.collectAsState()
    val unVerifiedProfilesFlow = lenderDashboardViewModel.unVerifiedBorrowerUserProfiles.collectAsState()

    BorrowerProfilesScreen(
        globalState = globalState,
        onClose = onClose,
        onSearchValueChange = { queriedName -> lenderDashboardViewModel.collectVerifiedBorrowerUserProfiles(queriedName) },
        onSearchValueSheetChange = { queriedName -> lenderDashboardViewModel.collectUnVerifiedBorrowerUserProfiles(queriedName) },
        verifiedProfiles = verifiedProfilesFlow,
        unVerifiedProfiles = unVerifiedProfilesFlow,
    )
}