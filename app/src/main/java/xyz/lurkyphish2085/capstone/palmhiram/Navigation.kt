package xyz.lurkyphish2085.capstone.palmhiram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.OTPRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.RegistrationRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.VerificationRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.WelcomeRoute

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val DASHBOARD_ROUTE = "dashboard"
    const val REGISTRATION_ROUTE = "registration"
    const val OTP_ROUTE = "otp"
    const val VERIFICATION_ROUTE = "verification"
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
// VIEWMODEL HERE,
fun PalmHiramNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.WELCOME_ROUTE,
        modifier = modifier
    ) {
        // TODO: INSERT SCREENS HERE
        composable(Destinations.WELCOME_ROUTE) {
            WelcomeRoute(
                onLogin = { navController.navigate(Destinations.DASHBOARD_ROUTE) },
                onRegister = { navController.navigate(Destinations.REGISTRATION_ROUTE) }
            )
        }
        composable(Destinations.REGISTRATION_ROUTE) {
            RegistrationRoute(
                onSubmit = { navController.navigate(Destinations.OTP_ROUTE) }
            )
        }
        composable(Destinations.OTP_ROUTE) {
            OTPRoute(
                onSubmit = { navController.navigate(Destinations.VERIFICATION_ROUTE) }
            )
        }
        composable(Destinations.VERIFICATION_ROUTE) {
            VerificationRoute(
                onSubmit = { navController.navigate(Destinations.DASHBOARD_ROUTE) }
            )
        }
        composable(Destinations.DASHBOARD_ROUTE) {

        }
    }
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