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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.CircularProgressLoadingIndicator
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ScreenTitleBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TextFieldWithError
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme

@ExperimentalMaterial3Api
@Composable
fun AccountVerificationScreen(
    globalState: FunniGlobalViewModel,
    viewModel: AuthViewModel?,
    onSuccessVerification: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoadingProgressIndicatorOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isConfirmButtonEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    var verificationCodeInput by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val retrievedVerificationCodeFlow = viewModel?.retrievedVerificationCodeFlow?.collectAsState()
    val verificationConfirmationFlow = viewModel?.verifiedUserConfirmationFlow?.collectAsState()

    retrievedVerificationCodeFlow?.value?.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                        .show()

                    isLoadingProgressIndicatorOpen = false
                }
            }
            Resource.Loading -> { isLoadingProgressIndicatorOpen = true }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: Code: ${it.result.code} for Email: ${it.result.email} retrieved to compare with ${verificationCodeInput}", Toast.LENGTH_LONG)
                        .show()

                    isLoadingProgressIndicatorOpen = false
                    
                    if (it.result.code == verificationCodeInput) {
                        viewModel?.verifyUser(globalState.user.userId)
                    } else {
                        Toast.makeText(context, "Input didn't match :(", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            else -> {}
        }
    }

    verificationConfirmationFlow?.value?.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                        .show()

                    isLoadingProgressIndicatorOpen = false
                }
            }
            Resource.Loading -> { isLoadingProgressIndicatorOpen = true }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: User ${it.result.name}:${it.result.email} is now Verified!", Toast.LENGTH_LONG)
                        .show()

                    isLoadingProgressIndicatorOpen = false

                    onSuccessVerification()
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = { ScreenTitleBar("Verification") },
        bottomBar = { 
            WideButton(
                enabled = isConfirmButtonEnabled,
                text = "Get Verified",
                onclick = {
                    viewModel?.retrieveValidVerificationCode(globalState.user.email)
                },
            )
        },
        modifier = modifier
    ) { paddingValues ->
        AccountVerificationInputContent(
            onFieldChange = { fieldsValid, text ->
                isConfirmButtonEnabled = fieldsValid
                verificationCodeInput = text
            },
            Modifier.padding(paddingValues)
        )
    }

    CircularProgressLoadingIndicator(isLoadingProgressIndicatorOpen)
}

@ExperimentalMaterial3Api
@Composable
fun AccountVerificationInputContent(
    onFieldChange: (areFieldsValid: Boolean, text: String) -> Unit = { b: Boolean, s: String -> },
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
                label = "Verification Code",
                errorText = "Field is empty",
                keyboardType = KeyboardType.Number,
                onValueChange = { text ->
                    onFieldChange(text.isNotBlank(), text)
                }
            )

            Spacer(modifier = Modifier.heightIn(128.dp))
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AccountVerificationScreenPreview() {
    PalmHiramTheme {
        Surface {
            AccountVerificationScreen(globalState = FunniGlobalViewModel(),viewModel = null, onSuccessVerification = {})
        }
    }
}