package xyz.lurkyphish2085.capstone.palmhiram.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.NameField
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.PhoneField
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ScreenTitleBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@ExperimentalMaterial3Api
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { ScreenTitleBar(text = "PERSONAL INFO", modifier = Modifier.padding(all = 16.dp)) },
        bottomBar = {
            WideButton(
                text = "REGISTER",
                onclick = { /*TODO*/ },
                modifier = Modifier.padding(all = 16.dp)
            )
        },
    ) { padding ->
        SignUpContent(Modifier.padding(padding))
    }
}

@ExperimentalMaterial3Api
@Composable
fun SignUpContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            NameField( label = "First Name"
            )
            NameField(
                label = "Last Name"
            )
            PhoneField()

            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RegistrationScreenPreview() {
    PalmHiramTheme {
        Surface {
            RegistrationScreen()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview(name = "light", showBackground = true, heightDp = 320, uiMode = Configuration.UI_MODE_NIGHT_NO)
fun SignUpContentPreview() {
    PalmHiramTheme {
        Surface {
            SignUpContent()
        }
    }
}