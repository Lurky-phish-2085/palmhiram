package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.InputValidationUtil


@ExperimentalMaterial3Api
@Composable
fun TextFieldWithError(
    passingCondition: (text: String) -> Boolean,
    onValueChange: (text: String) -> Unit = {},
    value: String = "",
    label: String,
    isError: Boolean = false,
    errorText: String = "Not a valid value",
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable {
        mutableStateOf(value)
    }

    var enableError by rememberSaveable {
        mutableStateOf(isError)
    }

    LaunchedEffect(isError) {
        enableError = isError
    }

    Column(modifier) {
        CustomTextField(
            value = text,
            label = label,
            isError = enableError,
            onValueChange = {
                text = it
                enableError = !passingCondition(text)
                onValueChange(text)
            },
            keyboardOptions = KeyboardOptions().copy(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    enableError = !passingCondition(text)
                    onValueChange(text)
                }
            ),
        )

        AnimatedVisibility(visible = enableError) {
            ErrorText(errorText)
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun TextFieldWithErrorPreview() {
    PalmHiramTheme {
        Surface {
            TextFieldWithError(
                passingCondition = { text ->
                    text.length < 10
                },
                label = "Only Text shorter than 10 chars",
                errorText = "Text should be < 10 characters only!"
            )
        }
    }
}