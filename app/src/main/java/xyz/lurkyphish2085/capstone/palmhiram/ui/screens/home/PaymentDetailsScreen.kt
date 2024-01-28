package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
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
            modifier.padding(padding)
        ) {
            Text(text = "Payment Status")
            val paymentStatus =
                when(PaymentScheduleDateStatus.valueOf(globalState.selectedPaymentDateItem.status)) {
                    PaymentScheduleDateStatus.APPROVED -> "APPROVED"
                    PaymentScheduleDateStatus.APPROVAL -> "UNDER APPROVAL"
                    PaymentScheduleDateStatus.PENDING -> "PENDING"
                }
            Text(text = paymentStatus)

            Text(text = "Amount")
            Text(text = "₱ ${Money.parseActualValue(globalState.selectedLoanTransactionItem.paymentPerSchedule)}")
            Text(text = "Date due")
            Text(text = "${DateTimeUtils.formatToISO8601NullDate(globalState.selectedPaymentDateItem.date?.toDate())}")

            Text(text = "Borrower Remarks")
            Text(text = globalState.selectedPaymentItem.borrowerRemarks)

            val base64EncodedBorrowerProofImage = globalState.selectedPaymentItem.borrowerProofImage
            val borrowerProofImageBitmap = ImageUtils.decodeBase64ToBitmap(base64EncodedBorrowerProofImage)
            Text(text = "Borrower Proof")
            Image(
                bitmap = borrowerProofImageBitmap?.asImageBitmap()!!,
                contentDescription = null,
                modifier = Modifier.size(512.dp)
            )

            val base64EncodedLenderProofImage = globalState.selectedPaymentItem.lenderProofImage
            Text(text = "Lender Proof")
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