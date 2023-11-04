package xyz.lurkyphish2085.capstone.palmhiram.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.InputValidationUtils

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
            InputValidationUtils.validateName(name)
        },
        label = label,
        errorText = "Not a valid name",
        onValueChange = { name ->
            onValueChange(name, InputValidationUtils.validateName(name))
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