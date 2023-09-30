package xyz.lurkyphish2085.capstone.palmhiram.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ScreenTitleBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@ExperimentalMaterial3Api
@Composable
fun OTPScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { ScreenTitleBar("OTP", modifier = Modifier.padding(all = 16.dp)) },
        bottomBar = { 
            WideButton(
                onclick = { /*TODO*/ },
                text = "CONFIRM",
                modifier = Modifier.padding(all = 16.dp)
            )
        }

    ) { paddingValues ->
        OTPInputContent(modifier.padding(paddingValues))
    }
}

@ExperimentalMaterial3Api
@Composable
fun OTPInputContent(
    modifier: Modifier = Modifier
) {

}

@ExperimentalMaterial3Api
@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OTPScreenPreview() {
    PalmHiramTheme {
        Surface {
            OTPScreen()
        }
    }
}