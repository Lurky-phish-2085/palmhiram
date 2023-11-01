package xyz.lurkyphish2085.capstone.palmhiram

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.ApplyLoanRoute
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.ApplyLoanScreen
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardRoute
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
            const val PROFILE_DRAWER_ROUTE = "${DASHBOARD_ROUTE}/profile-drawer"
            const val NOTIFICATIONS_ROUTE = "${DASHBOARD_ROUTE}/notifications"

            const val APPLY_LOAN_ROUTE = "${DASHBOARD_ROUTE}/apply-loan"
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
        var globalAuthViewModel: AuthViewModel? = null

        // TODO: INSERT SCREENS HERE
        navigation(
            route = Destinations.AUTH_NAV_ROUTE,
            startDestination = Destinations.WELCOME_ROUTE,
        ) {
            composable(Destinations.WELCOME_ROUTE) {
                val viewModel = it.sharedViewModel<AuthViewModel>(navController)
                WelcomeRoute(
//                    viewModel = it.sharedViewModel(navController),
                    viewModel = viewModel,
                    onLoginNotVerified = {
                         navController.navigate(Destinations.VERIFICATION_ROUTE) {
                             popUpTo(Destinations.WELCOME_ROUTE) { inclusive = true }
                         }
                    },
                    onLogin = {
//                        navController.navigate(Destinations.HOME_NAV_ROUTE) {
                        navController.navigate(Destinations.DASHBOARD_ROUTE) {
                            popUpTo(Destinations.WELCOME_ROUTE) { inclusive = true }
                            globalAuthViewModel = viewModel
                        }
                    },
                    onRegister = { navController.navigate(Destinations.REGISTRATION_ROUTE) }
                )
            }
            composable(
                route = Destinations.REGISTRATION_ROUTE,
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
        composable(
            route = Destinations.DASHBOARD_ROUTE,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(450, easing = LinearEasing)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(450, easing = LinearEasing)
                )
            }
        ) {
            DashboardRoute(
                onProfileClick = {
                     navController.navigate(Destinations.PROFILE_DRAWER_ROUTE)
                },
                onNotificationsClick = { /*TODO*/ },
                onBorrowerApplyLoanClick = {
                    navController.navigate(Destinations.APPLY_LOAN_ROUTE)
                },
                onBorrowerPayLoanClick = {},
                onLenderCollectLoanClick = {},
                onLenderGiveLoanClick = {},
                authViewModel = globalAuthViewModel!!,
                borrowerDashboardViewModel = it.sharedViewModel(navController),
                lenderDashboardViewModel = it.sharedViewModel(navController),
            )
        }
        composable(
            route = Destinations.PROFILE_DRAWER_ROUTE,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(600, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(2000, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            }
        ) {
            DashboardSideProfileScreenRoute(
                onCloseClick = {
                    navController.navigateUp()
                },
                onLogOutClick = {
                    navController.navigate(Destinations.WELCOME_ROUTE) {
                        popUpTo(Destinations.DASHBOARD_ROUTE) { inclusive = true }
                    }
                },
                onSettingsClick = { /*TODO*/ },
                onQuickHelpClick = { /*TODO*/ },
                onAboutClick = { /*TODO*/ },
                viewModel = globalAuthViewModel!!
            )
        }
        composable(
            route = Destinations.APPLY_LOAN_ROUTE,
        ) {
            ApplyLoanRoute(
                viewModel = it.sharedViewModel(navController),
                onSubmit = { navController.navigateUp() },
                onCloseClick = { navController.navigateUp() }
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