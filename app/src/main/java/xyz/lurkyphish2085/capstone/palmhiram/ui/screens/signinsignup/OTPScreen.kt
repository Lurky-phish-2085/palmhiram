package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ClickableTextWithLabel
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ScreenTitleBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TextFieldWithError
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@ExperimentalMaterial3Api
@Composable
fun OTPScreen(
    viewModel: AuthViewModel?,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isConfirmButtonEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    var shouldSendOtpEmail by rememberSaveable {
        mutableStateOf(true)
    }
    // TODO: DANGER!!! PREVENT THIS FROM GETTING CALLED EVERY RECOMPOSITION
    val context = LocalContext.current
    var otpResponseFlow = viewModel?.otpResponseFlow?.collectAsState()
    otpResponseFlow?.value?.let {
        when(it) {
            is Resource.Failure -> {
                Toast.makeText(context, "NOOOOOOOO: ${it.exception}", Toast.LENGTH_LONG).show()
            }
            Resource.Loading -> null
            is Resource.Success -> {
                Toast.makeText(context, "YESSS: ${it.result.otp}", Toast.LENGTH_SHORT).show()
            }
            else -> null
        }
    }
    // TODO: CREATES A LOT OF REQUEST THIS SHOULD ONLY BE CALLED OONCCEE!!!!
    if (shouldSendOtpEmail) {
        viewModel?.sendOtpEmail()
        shouldSendOtpEmail = false
    }

    Scaffold(
        topBar = { ScreenTitleBar("OTP") },
        bottomBar = {
            WideButton(
                enabled = isConfirmButtonEnabled,
                text = "CONFIRM",
                onclick = { onSubmit() },
            )
        },
        modifier = modifier
    ) { paddingValues ->
        OTPInputContent(
            onFieldChange = { fieldsValid ->
                isConfirmButtonEnabled = fieldsValid
            },
            Modifier.padding(paddingValues)
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
                },
                keyboardType = KeyboardType.Number
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
            OTPScreen( onSubmit = {}, viewModel = null)
        }
    }
}