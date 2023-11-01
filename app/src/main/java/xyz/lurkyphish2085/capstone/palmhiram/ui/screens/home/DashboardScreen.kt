package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopNavigationTab
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardRouteDestinations.CALENDAR_ROUTE
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardRouteDestinations.OVERVIEW_ROUTE
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home.DashboardRouteDestinations.REPORTS_ROUTE
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup.AuthViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.utils.UserRoles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    authViewModel: AuthViewModel?,
    borrowerDashboardViewModel: BorrowerDashboardViewModel?,
    lenderDashboardViewModel: LenderDashboardViewModel?,
    onProfileClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onLeftButtonClickAsBorrower: () -> Unit,
    onRightButtonClickAsBorrower: () -> Unit,
    onLeftButtonClickAsLender: () -> Unit,
    onRightButtonClickAsLender: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    var selectedTab by rememberSaveable {
        mutableStateOf(OVERVIEW_ROUTE)
    }

    Scaffold(
        topBar = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f, true))
                    IconButton(onClick = onNotificationsClick) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                DashBoardScreenTabRow(
                    onHomeClick = {
                        navController.navigate(OVERVIEW_ROUTE)
                        selectedTab = navController.currentDestination?.route!!
                    },
                    onCalendarClick = {
                        navController.navigate(CALENDAR_ROUTE)
                        selectedTab = navController.currentDestination?.route!!
                    },
                    onReportsClick = {
                        navController.navigate(REPORTS_ROUTE)
                        selectedTab = navController.currentDestination?.route!!
                    },
                    selectedTab = selectedTab,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier
    ) { padding ->
        HomeNavigation(
            navController = navController,
            role = UserRoles.valueOf(authViewModel?.userDetails?.role!!.uppercase()),
            borrowerDashboardViewModel = borrowerDashboardViewModel!!,
            lenderDashboardViewModel = lenderDashboardViewModel!!,
            onLeftButtonClickAsLender = onLeftButtonClickAsLender,
            onRightButtonClickAsLender = onRightButtonClickAsLender,
            onLeftButtonClickAsBorrower = onLeftButtonClickAsBorrower,
            onRightButtonClickAsBorrower = onRightButtonClickAsBorrower,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DashBoardScreenTabRow(
    onHomeClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onReportsClick: () -> Unit,
    selectedTab: String = "",
    modifier: Modifier = Modifier
) {
    var selectedTabName by rememberSaveable {
        mutableStateOf(selectedTab)
    }
    
    LaunchedEffect(selectedTab) {
        selectedTabName = selectedTab
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .selectableGroup()
    ) {
        TopNavigationTab(
            selected = selectedTabName == OVERVIEW_ROUTE,
            onClick = onHomeClick,
            text = "Home",
            modifier = Modifier
                .weight(1f)
        )
        TopNavigationTab(
            selected = selectedTabName == CALENDAR_ROUTE,
            onClick = onCalendarClick,
            text = "Calendar",
            modifier = Modifier
                .weight(1f)
        )
        TopNavigationTab(
            selected = selectedTabName == REPORTS_ROUTE,
            onClick = onReportsClick,
            text = "Reports",
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenPreview() {
    PalmHiramTheme {
        Surface {
            DashboardScreen(
                onProfileClick = {},
                onNotificationsClick = {},
                authViewModel = null,
                borrowerDashboardViewModel = null,
                lenderDashboardViewModel = null,
                onRightButtonClickAsBorrower = {},
                onLeftButtonClickAsBorrower = {},
                onRightButtonClickAsLender = {},
                onLeftButtonClickAsLender = {},
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
        }
    }
}
