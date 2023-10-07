package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.InputValidationUtil

@ExperimentalMaterial3Api
@Composable
fun NameField(
    label: String = "",
    onValueChange: (text: String, isValid: Boolean) -> Unit = { s: String, b: Boolean -> },
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }

    var isNotValid by rememberSaveable {
        mutableStateOf(isError)
    }

    TextFieldWithError(
        passingCondition = { name ->
            InputValidationUtil.validateName(name)
        },
        label = label,
        errorText = "Not a valid name",
        onValueChange = { name ->
            onValueChange(name, InputValidationUtil.validateName(name))
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun NameFieldPreview() {
    PalmHiramTheme {
        Surface {
            NameField("Name")
        }
    }
}