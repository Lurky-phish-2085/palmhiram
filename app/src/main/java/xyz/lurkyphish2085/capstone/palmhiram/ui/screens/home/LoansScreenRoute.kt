package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel
import xyz.lurkyphish2085.capstone.palmhiram.utils.UserRoles

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun LoansScreenRoute(
    globalState: FunniGlobalViewModel,
    onClose: () -> Unit,
    onSelectOngoingLoanAsBorrower: () -> Unit,
    onSelectSettledLoanAsBorrower: () -> Unit,
    onSelectCancelledLoanAsBorrower: () -> Unit,
    onSelectOngoingLoanAsLender: () -> Unit,
    onSelectSettledLoanAsLender: () -> Unit,
    onSelectCancelledLoanAsLender: () -> Unit,
    authViewModel: AuthViewModel,
    borrowerDashboardViewModel: BorrowerDashboardViewModel,
    lenderDashboardViewModel: LenderDashboardViewModel,
) {
    LoansScreen(
        globalState = globalState,
        onSelectOngoingLoanAsBorrower = onSelectOngoingLoanAsBorrower,
        onSelectSettledLoanAsBorrower = onSelectSettledLoanAsBorrower,
        onSelectCancelledLoanAsBorrower = onSelectCancelledLoanAsBorrower,
        onSelectOngoingLoanAsLender = onSelectOngoingLoanAsLender,
        onSelectSettledLoanAsLender = onSelectSettledLoanAsLender,
        onSelectCancelledLoanAsLender = onSelectCancelledLoanAsLender,
        role = UserRoles.valueOf(authViewModel?.userDetails?.role?.uppercase()!!),
        borrowerDashboardViewModel = borrowerDashboardViewModel,
        lenderDashboardViewModel = lenderDashboardViewModel,
        onClose = onClose,
        modifier = Modifier
            .padding(top = 16.dp)
    )
}