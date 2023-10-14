package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup

import android.content.res.Configuration
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresExtension
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.RetrofitInstance
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.NameField
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.PhoneField
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ScreenTitleBar
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import java.io.IOException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@ExperimentalMaterial3Api
@Composable
fun RegistrationScreen(
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isConfirmButtonEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    val TAG = "MainActivity"
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            val response = try {
                RetrofitInstance.api.getPing()
            } catch (e: IOException) {
                Log.e(TAG, "No internet connection for response")
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "Unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                val message = response.body()!!
                Toast.makeText(context,  message.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    Scaffold(
        topBar = { ScreenTitleBar(text = "PERSONAL INFO") },
        bottomBar = {
            WideButton(
                enabled = isConfirmButtonEnabled,
                text = "REGISTER",
                onclick = { onSubmit() },
            )
        },
        modifier = modifier
    ) { padding ->
        SignUpContent(
            onFieldChange = { fieldsValid ->
                isConfirmButtonEnabled = fieldsValid
            },
            Modifier.padding(padding)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun SignUpContent(
    onFieldChange: (areFieldsValid: Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var firstNameValid by rememberSaveable {
        mutableStateOf(false)
    }
    var lastNameValid by rememberSaveable {
        mutableStateOf(false)
    }
    var phoneValid by rememberSaveable {
        mutableStateOf(false)
    }

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
            Spacer(modifier = Modifier.weight(1f, true))

            NameField(
                label = "First Name",
                onValueChange = { text, isValid ->
                    firstNameValid = isValid
                    onFieldChange(firstNameValid && lastNameValid && phoneValid)
                }
            )
            NameField(
                label = "Last Name",
                onValueChange = { text, isValid ->
                    lastNameValid = isValid
                    onFieldChange(firstNameValid && lastNameValid && phoneValid)
                }
            )
            PhoneField(
                onValueChange = { text, isValid ->
                    phoneValid = isValid
                    onFieldChange(firstNameValid && lastNameValid && phoneValid)
                }
            )

            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@ExperimentalMaterial3Api
@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RegistrationScreenPreview() {
    PalmHiramTheme {
        Surface {
            RegistrationScreen(onSubmit = {})
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