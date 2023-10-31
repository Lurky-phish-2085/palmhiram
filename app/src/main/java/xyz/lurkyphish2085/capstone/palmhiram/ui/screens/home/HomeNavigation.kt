package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ContentSection
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardRouteDestinations.CALENDAR_ROUTE
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardRouteDestinations.OVERVIEW_ROUTE
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardRouteDestinations.REPORTS_ROUTE
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel

object DashboardRouteDestinations {

    const val ROOT_ROUTE = "dashboard"

    const val OVERVIEW_ROUTE = "$ROOT_ROUTE/overview"
    const val CALENDAR_ROUTE = "$ROOT_ROUTE/calendar"
    const val REPORTS_ROUTE = "$ROOT_ROUTE/reports"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigation(
    navController: NavHostController = rememberNavController(),
    role: String,
    borrowerDashboardViewModel: BorrowerDashboardViewModel,
    lenderDashboardViewModel: LenderDashboardViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = OVERVIEW_ROUTE,
        modifier = modifier
    ) {
        composable(OVERVIEW_ROUTE) {
            OverviewScreen(
                role = role,
                borrowerDashboardViewModel = borrowerDashboardViewModel,
                lenderDashboardViewModel = lenderDashboardViewModel
            )
        }
        composable(CALENDAR_ROUTE) {
            ContentSection {
                Text(text = "HELLO A")
                Text(text = "HELLO")
                Text(text = "HELLO")
            }
        }
        composable(REPORTS_ROUTE) {
            ContentSection {
                Text(text = "HELLO B")
                Text(text = "HELLO")
                Text(text = "HELLO")
            }
        }
    }
}