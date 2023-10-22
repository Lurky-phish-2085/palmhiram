package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "overview",
        modifier = modifier
    ) {
        composable("overview") {
            OverviewScreen()
        }
        composable("a") {
            OverviewSection {
                Text(text = "HELLO A")
                Text(text = "HELLO")
                Text(text = "HELLO")
            }
        }
        composable("b") {
            OverviewSection {
                Text(text = "HELLO B")
                Text(text = "HELLO")
                Text(text = "HELLO")
            }
        }
        composable("c") {
            OverviewSection {
                Text(text = "HELLO C")
                Text(text = "HELLO")
                Text(text = "HELLO")
            }
        }
    }
}

@Preview
@Composable
fun HomeNavigationPreview() {
    PalmHiramTheme {
        Surface {
            HomeNavigation()
        }
    }
}