package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.CircularProgressLoadingIndicator
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ConfirmationDialog
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.LoanTransactionItemCard
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel


@Composable
fun LenderDeclineLoanScreen(
    onClose: () -> Unit,
    onSubmit: () -> Unit,
    globalState: FunniGlobalViewModel,
    viewModel: LenderLoanOverviewViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val remarksFlow = viewModel.cancellationRemarks.collectAsState()
    var areAllFieldsOkay by rememberSaveable {
        mutableStateOf(false)
    }

    val isRemarksOkay = {
        (remarksFlow?.value?.length in 1..257 ) && remarksFlow.value.isNotBlank()
    }

    var isSuccessDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isFailDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }
    CircularProgressLoadingIndicator(isLoading)
    ConfirmationDialog(
        isOpen = isSuccessDialogOpen,
        positiveButtonOnly = true,
        onPositiveClick = {
            isSuccessDialogOpen = false
            onSubmit()
        },
        onNegativeClick = {},
        onDismissRequest = {},
        onClose = {
            isSuccessDialogOpen = false
            onSubmit()
        },
        title = "Loan Cancelled",
        icon = Icons.Default.Check,
        headline = "Loan Cancelled Successfully",
        description = "The loan is no longer available.",
        positiveButtonText = "OKAY",
        negativeButtonText = ""
    )
    ConfirmationDialog(
        isOpen = isFailDialogOpen,
        positiveButtonOnly = true,
        onPositiveClick = {
            isFailDialogOpen = false
            onClose()
        },
        onNegativeClick = {},
        onDismissRequest = {},
        onClose = { isFailDialogOpen = false },
        title = "Submission Failed",
        icon = Icons.Default.WarningAmber,
        headline = "Something went wrong",
        description = "Please try again",
        positiveButtonText = "OKAY",
        negativeButtonText = ""
    )

    val onCancelLoanClick = {
        viewModel.selectedLoanTransaction = globalState.selectedLoanTransactionItem
        viewModel.cancelLoanTransaction()
    }

    val cancelLoanFlow = viewModel.cancelLoanFlow.collectAsState()
    cancelLoanFlow.value.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                        .show()

                    isLoading = false
                    isFailDialogOpen = true
                }
            }

            is Resource.Loading -> {
                isLoading = true
            }

            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: ${it.result.id}", Toast.LENGTH_SHORT)
                        .show()

                    isLoading = false
                    isSuccessDialogOpen = true
                }
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Decline Loan",
                onClose = onClose
            )
        },
        bottomBar = {
            WideButton(
                enabled = remarksFlow.value.length in 1..257 && remarksFlow.value.isNotBlank(),
                text = "SUBMIT",
                onclick = {onCancelLoanClick()}
            )
        },
        modifier = modifier
    ) { padding ->

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "You are about to cancel this loan transaction.")
            LoanTransactionItemCard(
                onClick = {},
                balanceName = "TOTAL AMOUNT TO COLLECT",
                transactionDetails = globalState.selectedLoanTransactionItem
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = remarksFlow.value,
                onValueChange = {
                    viewModel.updateCancellationRemarksField(it)
                    areAllFieldsOkay = isRemarksOkay()
                },
                label = { Text(text = "Write your remarks here") },
                minLines = 12,
                maxLines = 12,
            )
            Text("${remarksFlow.value.length}/256", color = if (isRemarksOkay()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}