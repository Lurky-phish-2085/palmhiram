package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.OTP
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
                LaunchedEffect(Unit) {
                    //Toast.makeText(context, "YESSS: ${it.result.otp}", Toast.LENGTH_SHORT).show()
                    viewModel?.storeOtp(it.result.otp)
                    Toast.makeText(context, it.result.otp, Toast.LENGTH_SHORT).show()
                }
            }
            else -> null
        }
    }
    // TODO: CREATES A LOT OF REQUEST THIS SHOULD ONLY BE CALLED OONCCEE!!!!
    LaunchedEffect(shouldSendOtpEmail) {
        if (shouldSendOtpEmail) {
            viewModel?.sendOtpEmail()
            shouldSendOtpEmail = false
        }
    }

    var otpInput by rememberSaveable {
        mutableStateOf("")
    }

    var retrivedOtpFlow = viewModel?.retrievedOtpFlow?.collectAsState()
    // TODO: sum scary stuff, im telling you
    Scaffold(
        topBar = { ScreenTitleBar("OTP") },
        bottomBar = {
            WideButton(
                enabled = isConfirmButtonEnabled,
                text = "CONFIRM",
                onclick = {
                    viewModel?.retrieveValidOtp()
                },
            )
        },
        modifier = modifier
    ) { paddingValues ->
        OTPInputContent(
            retrivedOtpFlow = retrivedOtpFlow,
            onSuccess = {
                val retrievedOtp: OTP? = retrivedOtpFlow?.value?.let {
                    when(it) {
                        is Resource.Failure -> null
                        Resource.Loading -> null
                        is Resource.Success -> it.result
                        else -> null
                    }
                }

                val email = viewModel?.fields?.email
                val ourOtp = OTP(otpInput, email!!)

                if (ourOtp.code == retrievedOtp?.code) {
                    onSubmit()
                }
            },
            onFieldChange = { fieldsValid, otpCode ->
                isConfirmButtonEnabled = fieldsValid
                otpInput = otpCode
            },
            Modifier.padding(paddingValues)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun OTPInputContent(
    retrivedOtpFlow: State<Resource<OTP>?>?,
    onSuccess: () -> Unit,
    onFieldChange: (areFieldsValid: Boolean, otpCode: String) -> Unit = { b: Boolean, s: String -> },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(modifier) {
        retrivedOtpFlow?.value?.let {
            when(it) {
                is Resource.Failure -> {
                    Toast.makeText(context, "${it.exception}", Toast.LENGTH_LONG)
                        .show()
                }
                Resource.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is Resource.Success -> {
                    Toast.makeText(context, "YESSS ${it.result.toString()}", Toast.LENGTH_LONG)
                        .show()

                    onSuccess()
                }

                else -> null
            }
        }

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
                onValueChange = { text ->
                    onFieldChange(text.isNotBlank(), text)
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