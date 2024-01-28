package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.CircularProgressLoadingIndicator
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.ConfirmationDialog
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.utils.PaymentScheduleDateStatus

@Composable
fun BorrowerConfirmLoanPaymentScreen(
    globalState: FunniGlobalViewModel,
    viewModel: BorrowerConfirmLoanPaymentViewModel,
    onClose: () -> Unit,
    onSubmitSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }
    var isSuccessDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isFailDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val selectedImageUriFlow = viewModel?.selectedImageUri?.collectAsState()
    val selectedBitmapFlow = viewModel?.selectedBitmap?.collectAsState()
    val bitmapBase64Flow = viewModel?.bitmapBase64String?.collectAsState()
    val remarksFlow = viewModel?.remarks?.collectAsState()
    val areAllFieldsFlow = viewModel?.areAllFieldsOkay?.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
              viewModel?.updateImageBox(context, uri)
        }
    )

    val goToGallery = { launcher.launch("image/*") }

    val submitPaymentConfirmation = {
        viewModel.loanTransactionItem = globalState.selectedLoanTransactionItem
        viewModel.paymentScheduleDateItem = globalState.selectedPaymentDateItem
        viewModel.submitPaymentConfirmation()
    }

    val submissionFlow = viewModel.submissionFlow.collectAsState()
    submissionFlow.value.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                        .show()

                    isLoading = false
                    isFailDialogOpen = true
                }
            }
            is Resource.Loading -> { isLoading = true }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: ${it.result.id}", Toast.LENGTH_SHORT)
                        .show()

                    isLoading = false

                    viewModel.updatePaymentScheduleDateStatus(PaymentScheduleDateStatus.APPROVAL)
                }
            }
            else -> {}
        }
    }

    val updatePaymentDateFlow = viewModel.updatePaymentDateFlow.collectAsState()
    updatePaymentDateFlow.value.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "FAIL: ${it.exception.message}", Toast.LENGTH_LONG)
                        .show()

                    isLoading = false
                    isFailDialogOpen = true
                }
            }
            Resource.Loading -> { isLoading = true }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "SUCCESS: ${it.result.status}", Toast.LENGTH_SHORT)
                        .show()

                    isLoading = false
                    isSuccessDialogOpen = true
                }
            }
            else -> {}
        }
    }

    CircularProgressLoadingIndicator(isOpen = isLoading)
    ConfirmationDialog(
        isOpen = isSuccessDialogOpen,
        positiveButtonOnly = true,
        onPositiveClick = {
            isSuccessDialogOpen = false
            onSubmitSuccess()
        },
        onNegativeClick = {},
        onDismissRequest = {},
        onClose = { isSuccessDialogOpen = false },
        title = "Confirmation Submitted",
        icon = Icons.Default.Check,
        headline = "Your payment is now under approval.",
        description = "Please wait for the lender's response",
        positiveButtonText = "OKAY",
        negativeButtonText = ""
    )
    ConfirmationDialog(
        isOpen = isFailDialogOpen,
        positiveButtonOnly = true,
        onPositiveClick = { isFailDialogOpen = false },
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

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Confirm Payment",
                onClose = onClose
            )
        },
        bottomBar = {
            WideButton(
                enabled = areAllFieldsFlow?.value!!,
                text = "SUBMIT",
                onclick = { submitPaymentConfirmation() }
            )
        },
        modifier = modifier
    ) { padding ->
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "To confirm the payment, submit photos as proof.")
            
            Button(onClick = goToGallery) {
                Text(text = "SELECT IMAGE")
            }

            if (selectedImageUriFlow?.value == null) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(128.dp)
                )
                Text(text = "Your selected image will appear here.")
            } else {
                Text(text = bitmapBase64Flow?.value?.length?.toString()!!)
                Image(
                    painter = BitmapPainter(selectedBitmapFlow?.value!!),
                    contentDescription = null,
                    modifier = Modifier.size(512.dp)
                )
            }

            OutlinedTextField(
                value = remarksFlow?.value!!,
                onValueChange = { viewModel.updateRemarks(it) },
                label = { Text("Write Your Remarks Here") },
                minLines = 12,
                maxLines = 12,
            )

            Text("${remarksFlow.value.length}/256", color = if (remarksFlow?.value?.length in 1..257) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}