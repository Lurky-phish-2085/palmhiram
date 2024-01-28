package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.grpc.internal.LogExceptionRunnable
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.NothingToSeeHere
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.TopBarWithBackButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.components.WideButton
import xyz.lurkyphish2085.capstone.palmhiram.ui.screens.FunniGlobalViewModel
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.ImageUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.Money
import xyz.lurkyphish2085.capstone.palmhiram.utils.PaymentScheduleDateStatus

@Composable
fun PaymentDetailsScreen(
    globalState: FunniGlobalViewModel,
    onClose: () -> Unit,
    viewModel: BorrowerConfirmLoanPaymentViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopBarWithBackButton(
                text = "Payment Details",
                onClose = onClose
            )
        },
        modifier = modifier
    ) { padding ->

        Column(
            modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Payment Status",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            val paymentStatus =
                when(PaymentScheduleDateStatus.valueOf(globalState.selectedPaymentDateItem.status)) {
                    PaymentScheduleDateStatus.APPROVED -> "APPROVED"
                    PaymentScheduleDateStatus.APPROVAL -> "UNDER APPROVAL"
                    PaymentScheduleDateStatus.PENDING -> "PENDING"
                }
            Text(
                text = paymentStatus,
                style = MaterialTheme.typography.headlineSmall,
            )

            Text(
                text = "Amount",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                text = "₱ ${Money.parseActualValue(globalState.selectedLoanTransactionItem.paymentPerSchedule)}",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "Date due",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            val a = globalState.selectedPaymentItem.date?.toDate()
            val b = DateTimeUtils.formatToISO8601NullDate(a)
            Text(
                text = b ?: "null LMAO XD",
                style = MaterialTheme.typography.headlineSmall,
            )

            Text(text = "Borrower Remarks:", fontWeight = FontWeight.Bold)
            BasicText(text = globalState.selectedPaymentItem.borrowerRemarks, maxLines = 12)

            val base64EncodedBorrowerProofImage = globalState.selectedPaymentItem.borrowerProofImage
            val borrowerProofBitmap = ImageUtils.decodeBase64ToBitmap(base64EncodedBorrowerProofImage)
            val borrowerProofImageBitmap = borrowerProofBitmap?.asImageBitmap()
            Text(text = "Borrower Proof:", fontWeight = FontWeight.Bold)
            Image(
                bitmap = borrowerProofImageBitmap!!,
                contentDescription = null,
                modifier = Modifier.size(512.dp)
            )

            Text(text = "Lender Remarks:", fontWeight = FontWeight.Bold)
            BasicText(text = globalState.selectedPaymentItem.lenderRemarks, maxLines = 12)

            val base64EncodedLenderProofImage = globalState.selectedPaymentItem.lenderProofImage
            Text(text = "Lender Proof:", fontWeight = FontWeight.Bold)
            if (base64EncodedLenderProofImage.isBlank()) {
                NothingToSeeHere(Modifier.fillMaxWidth())
            } else {
                val lenderProofImageBitmap = ImageUtils.decodeBase64ToBitmap(base64EncodedLenderProofImage)
                Image(
                    bitmap = lenderProofImageBitmap?.asImageBitmap()!!,
                    contentDescription = null,
                    modifier = Modifier.size(512.dp)
                )
            }
        }
    }
}