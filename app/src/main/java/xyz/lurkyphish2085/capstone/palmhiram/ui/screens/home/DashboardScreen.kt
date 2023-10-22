package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {

}


@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenPreview() {
    PalmHiramTheme {
        Surface {
            DashboardScreen()
        }
    }
}
