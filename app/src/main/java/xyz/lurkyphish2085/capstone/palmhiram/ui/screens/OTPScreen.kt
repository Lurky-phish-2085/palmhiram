package xyz.lurkyphish2085.capstone.palmhiram.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ClickableTextWithLabel
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ScreenTitleBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TextFieldWithError
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@ExperimentalMaterial3Api
@Composable
fun OTPScreen(
    modifier: Modifier = Modifier
) {
    var isConfirmButtonEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = { ScreenTitleBar("OTP", modifier = Modifier.padding(all = 16.dp)) },
        bottomBar = { 
            WideButton(
                enabled = isConfirmButtonEnabled,
                text = "CONFIRM",
                onclick = { /*TODO*/ },
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    ) { paddingValues ->
        OTPInputContent(
            onFieldChange = { fieldsValid ->
                isConfirmButtonEnabled = fieldsValid
            },
            modifier.padding(paddingValues)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun OTPInputContent(
    onFieldChange: (areFieldsValid: Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TextFieldWithError(
                passingCondition = { text ->
                    text.isNotBlank()
                },
                label = "OTP Code",
                errorText = "Field is empty",
                onValueChange = {
                    onFieldChange(it.isNotBlank())
                }
            )

            Spacer(modifier = Modifier.heightIn(128.dp))

            ClickableTextWithLabel(
                label = "Didn't received the code?",
                text = "Resend code",
                isEnabled = true,
                onClick =  {/* TODO */}
            )
        }
    }
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