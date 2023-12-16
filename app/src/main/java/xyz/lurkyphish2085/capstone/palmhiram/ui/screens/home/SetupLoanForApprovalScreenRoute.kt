package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SetupLoanApprovalScreenRoute(
    onClose: () -> Unit,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    Log.e("SELECTED LOAN DEBUG", "${lenderDashboardViewModel?.selectedLoanTransactionItem?.value}", )
    SetupLoanForApprovalScreen(
        balanceName = lenderDashboardViewModel.balanceName,
        lenderDashboardViewModel = lenderDashboardViewModel,
        onClose = onClose,
        modifier = Modifier.padding(all = 16.dp)
    )
}