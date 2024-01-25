package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TextFieldWithError
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.theme.PalmHiramTheme
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.InputValidationUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money

@ExperimentalMaterial3Api
@Composable
fun ApplyLoanScreen(
    viewModel: BorrowerDashboardViewModel?,
    onClose: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {

    val submissionFlow = viewModel?.submissionFlow?.collectAsState()

    var loanAmount by rememberSaveable {
        mutableStateOf("0.00")
    }
    var remarks by rememberSaveable {
        mutableStateOf("")
    }

    var allFieldsValid by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Apply Loan",
                onClose = onClose
            )
        },
        bottomBar = {
            WideButton(
                enabled = allFieldsValid,
                text = "SUBMIT",
                onclick = {
                    viewModel?.submitLoanRequest(Money.valueOf(loanAmount))
                }
            )
        },
        modifier = modifier
    ) { padding ->
        ApplyLoanScreenContent(
            submissionFlow = submissionFlow,
            onAmountChange = { loanValue, remarksValue, validity ->
                loanAmount = loanValue
                remarks = remarksValue
                allFieldsValid = validity
            },
            onSubmit = onSubmit,
            modifier = Modifier
                .padding(padding)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun ApplyLoanScreenContent(
    submissionFlow: State<Resource<LoanTransaction>?>?,
    onAmountChange: (String, String, Boolean) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var loanAmountField by rememberSaveable {
        mutableStateOf("0.00")
    }
    var remarksField by rememberSaveable {
        mutableStateOf("")
    }
    var remarksFieldValid by rememberSaveable {
        mutableStateOf(false)
    }

    fun isLoanAmountFieldValid(fieldInput: String): Boolean {
        return fieldInput.isNotBlank() && InputValidationUtils.validateNumericWithPoints(fieldInput)
    }
    fun isRemarksFieldValid(fieldInput: String): Boolean {
        return fieldInput.length in 1..257
    }
    fun updateFieldsValidity(loanAmountValue: String, remarksValue: String): Boolean {
        return isLoanAmountFieldValid(loanAmountValue) && isRemarksFieldValid(remarksValue)
    }
    fun updateFields(loanAmtField: String = loanAmountField, remarksFid: String = remarksField) {
        loanAmountField = loanAmtField
        remarksField = remarksFid
        onAmountChange(loanAmountField, remarksField, updateFieldsValidity(loanAmountField, remarksField))
    }


    Box(modifier) {
        submissionFlow?.value?.let {
            when(it) {
                is Resource.Failure -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(context, "YES: ${it.result}", Toast.LENGTH_LONG)
                            .show()

                        onSubmit()
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)
        ) {
            TextFieldWithError(
                label = "Enter amount",
                passingCondition = { isLoanAmountFieldValid(it) },
                onValueChange = {
                    loanAmountField = it
                    updateFields(loanAmtField = it)
                },
                keyboardType = KeyboardType.Number,
            )

            OutlinedTextField(
                value = remarksField,
                onValueChange = {
                    remarksField = it
                    updateFields(remarksFid = it)
                    remarksFieldValid = isRemarksFieldValid(it)
                },
                label = { Text("Write Your Remarks Here") },
                minLines = 12,
                maxLines = 12,
            )

            Text("${remarksField.length}/256", color = if (!remarksFieldValid) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline)
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "light", showBackground = true, heightDp = 640, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark", showBackground = true, heightDp = 640, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ApplyLoanScreenPreview() {
    PalmHiramTheme {
        Surface {
            ApplyLoanScreen(
                onClose = {},
                onSubmit = {},
                viewModel = null,
                modifier = Modifier
                    .padding(all = 16.dp)
            )
        }
    }
}