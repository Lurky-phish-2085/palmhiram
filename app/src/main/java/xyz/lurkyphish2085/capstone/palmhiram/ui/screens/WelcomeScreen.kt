package xyz.lurkyphish2085.capstone.palmhiram.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.InputValidationUtil

@ExperimentalMaterial3Api
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
) {
    // TODO: Add a background for the whole screen
    // TODO: Extract the the whole login/register form into a separate component
    Box(
        modifier = modifier,
    ) {
        // TODO: This row should have bolder text and changes from Login to Register
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
        ) {
            Text(text = "Login", style = MaterialTheme.typography.headlineLarge)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // TODO: Create this as a separate component, and create its state holder also
            // TODO: EmailField, validates email string if valid
            EmailField(onValueChange = {/*TODO*/})
            // TODO: Create this as a separate component, and create its state holder also
            // TODO: PasswordField, where it has validation and can display red text underneath. Has Re type password too
            PasswordField()

            Spacer(modifier = Modifier.height(128.dp))
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Don't have an account yet?",
                    style = MaterialTheme.typography.labelSmall,
                )
                ClickableText(
                    text = AnnotatedString(
                        text = "Register here",
                        spanStyle = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ),
                    onClick = {}
                )
            }
            }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            // TODO: This button should change from Login to Register
            Button(
                onClick = {},
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.weight(1f, true)
            ) {
                Text(text = "LOGIN")
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun EmailField(
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    var emailText by rememberSaveable {
        mutableStateOf("")
    }

    var emailNotValid by rememberSaveable {
        mutableStateOf(isError)
    }

    Column(modifier) {
        // Once the checkmark button clicked on the soft keeb, it will validate email
        OutlinedTextField(
            value = emailText,
            onValueChange = {
                emailText = it
                onValueChange(emailText)

                emailNotValid = !InputValidationUtil.validateEmail(emailText)
            },
            label = { Text(text = "Email")},
            trailingIcon = {
                if (emailNotValid) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Notifies invalid input")
                }
            },
            singleLine = true,
            isError = emailNotValid,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    emailNotValid = !InputValidationUtil.validateEmail(emailText)
                }
            )
        )

        // Display the error Text under the textfield if its not valid
        if (emailNotValid) {
            Text(
                text = "Not a valid email address",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun PasswordField(
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    var passwordText by rememberSaveable {
        mutableStateOf("")
    }

    var passwordNotValid by rememberSaveable {
        mutableStateOf(isError)
    }

    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier) {
        // Once the checkmark button clicked on the soft keeb, it will validate email
        OutlinedTextField(
            value = passwordText,
            onValueChange = {
                passwordText = it
                onValueChange(passwordText)

                passwordNotValid = !InputValidationUtil.validatePassword(passwordText)
            },
            label = { Text(text = "Password")},
            visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (showPassword) "Hide Password" else "Show Password"
                    )
                }
            },
            singleLine = true,
            isError = passwordNotValid,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    passwordNotValid = !InputValidationUtil.validatePassword(passwordText)
                }
            )
        )

        // Display the error Text under the textfield if its not valid
        if (passwordNotValid) {
            Text(
                text = "Not a valid password",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

/*
@ExperimentalMaterial3Api
@Composable
fun EmailField(
    emailState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = emailState.text,
        onValueChange = {
            emailState.text = it
        },
        label = { Text(text = "Email")},
        singleLine = true,
        isError = emailState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        modifier = Modifier
            .onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            }
    )

    emailState.getError()?.let { error -> TextFieldError(textError = error) }
}
*/

/*
@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        )
    }
}
*/

@Preview
@ExperimentalMaterial3Api
@Composable
fun PasswordFieldPreview() {
    Surface {
        Column(Modifier.padding(all = 16.dp)) {
            PasswordField()
        }
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun EmailFieldPreview() {
    Surface {
        Column(Modifier.padding(all = 16.dp)) {
            EmailField()
        }
    }
}

@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
fun WelcomeScreenPreview() {
    PalmHiramTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            WelcomeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp)
            )
        }
    }
}