package xyz.lurkyphish2085.capstone.palmhiram

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardScreen
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardSideProfileScreen
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardSideProfileScreenRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.OTPRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.RegistrationRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.VerificationRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.WelcomeRoute

private object Destinations {
    const val AUTH_NAV_ROUTE = "auth"
        const val WELCOME_ROUTE = "${AUTH_NAV_ROUTE}/welcome"
        const val REGISTRATION_ROUTE = "${AUTH_NAV_ROUTE}/registration"
        const val OTP_ROUTE = "${AUTH_NAV_ROUTE}/otp"
        const val VERIFICATION_ROUTE = "${AUTH_NAV_ROUTE}/verification"

        const val DASHBOARD_ROUTE = "dashboard"
        const val PROFILE_DRAWER_ROUTE = "profile-drawer"
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
                    onLoginNotVerified = {
                         navController.navigate(Destinations.VERIFICATION_ROUTE) {
                             popUpTo(Destinations.WELCOME_ROUTE) { inclusive = true }
                         }
                    },
                    onLogin = {
//                        navController.navigate(Destinations.HOME_NAV_ROUTE) {
                        navController.navigate(Destinations.DASHBOARD_ROUTE) {
                            popUpTo(Destinations.WELCOME_ROUTE) { inclusive = true }
                        }
                    },
                    onRegister = { navController.navigate(Destinations.REGISTRATION_ROUTE) }
                )
            }
            composable(
                route = Destinations.REGISTRATION_ROUTE,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(450, easing = LinearEasing)
                    ) + slideIntoContainer(
                        animationSpec = tween(450, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            450, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(450, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ) {
                RegistrationRoute(
                    viewModel = it.sharedViewModel(navController),
                    onSubmit = { navController.navigate(Destinations.OTP_ROUTE) }
                )
            }
            composable(
                route = Destinations.OTP_ROUTE,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(450, easing = LinearEasing)
                    ) + slideIntoContainer(
                        animationSpec = tween(450, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            450, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(450, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ) {
                OTPRoute(
                    viewModel = it.sharedViewModel(navController),
                    onSubmit = {
                        navController.navigate(Destinations.VERIFICATION_ROUTE) {
                            popUpTo(Destinations.WELCOME_ROUTE) { inclusive = true }
                        }
                    }
                )
            }
            composable(
                route = Destinations.VERIFICATION_ROUTE,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(450, easing = LinearEasing)
                    ) + slideIntoContainer(
                        animationSpec = tween(450, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            450, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(450, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ) {
                VerificationRoute(
                    viewModel = it.sharedViewModel(navController),
                    onSubmit = {
                        navController.navigate(Destinations.DASHBOARD_ROUTE) {
                            popUpTo(Destinations.AUTH_NAV_ROUTE) { inclusive = true }
                        }
                    }
                )
            }
        }
        composable(Destinations.DASHBOARD_ROUTE) {
            DashboardRoute(
                onProfileClick = {
                     navController.navigate(Destinations.PROFILE_DRAWER_ROUTE)
                },
                onNotificationsClick = { /*TODO*/ },
                viewModel = it.sharedViewModel(navController)
            )
        }
        composable(Destinations.PROFILE_DRAWER_ROUTE) {
            DashboardSideProfileScreenRoute(
                onCloseClick = {
                   navController.navigate(Destinations.DASHBOARD_ROUTE) {
                       popUpTo(Destinations.PROFILE_DRAWER_ROUTE) { inclusive = true }
                   }
                },
                onLogOutClick = {
                    navController.navigate(Destinations.WELCOME_ROUTE) {
                        popUpTo(Destinations.DASHBOARD_ROUTE) { inclusive = true }
                    }
                },
                onSettingsClick = { /*TODO*/ },
                onQuickHelpClick = { /*TODO*/ },
                onAboutClick = { /*TODO*/ },
                viewModel = it.sharedViewModel(navController)
            )
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