package xyz.lurkyphish2085.capstone.palmhiram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val DASHBOARD_ROUTE = "dashboard"
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun PalmHiramNavHost(
    // VIEWMODEL HERE,
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

        }
        composable(Destinations.DASHBOARD_ROUTE) {

        }
    }
}