package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme


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
        OutlinedTextField(
            value = text,
            label = { Text(text = label) },
            isError = enableError,
            onValueChange = {
                text = it
                enableError = !passingCondition(text)
                onValueChange(text)
            }
        )
        if (enableError) {
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