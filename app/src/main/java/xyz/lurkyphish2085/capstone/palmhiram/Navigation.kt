package xyz.lurkyphish2085.capstone.palmhiram

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ScreenTitleBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardScreen
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.HomeNavigation
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.OTPRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.RegistrationRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.VerificationRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.WelcomeRoute

object Destinations {
    const val AUTH_NAV_ROUTE = "auth"
        const val WELCOME_ROUTE = "${AUTH_NAV_ROUTE}/welcome"
        const val REGISTRATION_ROUTE = "${AUTH_NAV_ROUTE}/registration"
        const val OTP_ROUTE = "${AUTH_NAV_ROUTE}/otp"

        const val VERIFICATION_ROUTE = "${AUTH_NAV_ROUTE}/verification"

    const val HOME_NAV_ROUTE = "home"
        const val DASHBOARD_ROUTE = "${HOME_NAV_ROUTE}/dashboard"
            const val DASHBOARD_NAV_ROUTE = "${HOME_NAV_ROUTE}/${DASHBOARD_ROUTE}/dashboardNav"
                const val OVERVIEW_ROUTE = "${HOME_NAV_ROUTE}/${DASHBOARD_NAV_ROUTE}/overview"
                const val CALENDAR_ROUTE = "${HOME_NAV_ROUTE}/${DASHBOARD_NAV_ROUTE}/calender"
                const val REPORTS_ROUTE = "${HOME_NAV_ROUTE}/${DASHBOARD_NAV_ROUTE}/reports"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun PalmHiramNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.AUTH_NAV_ROUTE,
//        startDestination = "test",
        modifier = modifier
    ) {
        // TODO: INSERT SCREENS HERE
        navigation(
            route = Destinations.AUTH_NAV_ROUTE,
            startDestination = Destinations.WELCOME_ROUTE,
        ) {
            composable(Destinations.WELCOME_ROUTE) {
                WelcomeRoute(
                    viewModel = it.sharedViewModel(navController),
                    onLogin = {
                        navController.navigate(Destinations.HOME_NAV_ROUTE) {
                            popUpTo(Destinations.WELCOME_ROUTE) { inclusive = true }
                        }
                    },
                    onRegister = { navController.navigate(Destinations.REGISTRATION_ROUTE) }
                )
            }
            composable(Destinations.REGISTRATION_ROUTE) {
                RegistrationRoute(
                    viewModel = it.sharedViewModel(navController),
                    onSubmit = { navController.navigate(Destinations.OTP_ROUTE) }
                )
            }
            composable(Destinations.OTP_ROUTE) {
                OTPRoute(
                    viewModel = it.sharedViewModel(navController),
                    onSubmit = {
                        navController.navigate(Destinations.VERIFICATION_ROUTE) {
                            popUpTo(Destinations.WELCOME_ROUTE) { inclusive = true }
                        }
                    }
                )
            }
            composable(Destinations.VERIFICATION_ROUTE) {
                VerificationRoute(
                    viewModel = it.sharedViewModel(navController),
                    onSubmit = {
                        navController.navigate(Destinations.HOME_NAV_ROUTE) {
                            popUpTo(Destinations.AUTH_NAV_ROUTE) { inclusive = true }
                        }
                    }
                )
            }
        }
        composable("test") {
            DashboardScreen(
                modifier = Modifier.padding(all = 16.dp)
            )
        }
        navigation(
            route = Destinations.HOME_NAV_ROUTE,
            startDestination = Destinations.DASHBOARD_ROUTE,
        ) {
            composable(Destinations.DASHBOARD_ROUTE) {
                val authViewModel = it.sharedViewModel<AuthViewModel>(navController)
                authViewModel?.logout()

                // DEBUG TO CHECK IF AUTHVIEWMODEL STUFF STILL REMAINS HERE - NEGATIVE
                val context = LocalContext.current
                Toast.makeText(context, "${authViewModel.currentUser?.displayName}", Toast.LENGTH_LONG)
                    .show()

                navigation(
                    route = Destinations.DASHBOARD_NAV_ROUTE,
                    startDestination = Destinations.OVERVIEW_ROUTE,
                ) {

                }
            }
        }
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)
}

fun NavController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
    }