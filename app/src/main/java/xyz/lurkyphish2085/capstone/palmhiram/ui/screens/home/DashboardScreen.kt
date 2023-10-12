package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@ExperimentalMaterial3Api
@Composable
fun DashboardScreen(
    modifier: Modifier
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        modifier = modifier
    ) { paddingValues ->
        DashboardScreenContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun DashboardScreenContent(
    modifier: Modifier
) {

}

@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
fun DashboardScreenPreview() {
    PalmHiramTheme {
        Surface {
            //DashboardScreen()
        }
    }
}
