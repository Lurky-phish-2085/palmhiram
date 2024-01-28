package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentScheduleRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.data.models.Payment
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.ImageUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.PaymentScheduleDateStatus
import java.sql.Timestamp
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BorrowerConfirmLoanPaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val paymentScheduleRepository: PaymentScheduleRepository
): ViewModel() {

    val payments: Flow<List<Payment>>
        get() = paymentRepository.payments

    var paymentScheduleDateItem: PaymentScheduleDate = PaymentScheduleDate()
    var loanTransactionItem: LoanTransaction = LoanTransaction()

    var selectedImageUri: MutableStateFlow<Uri?>? = MutableStateFlow(null)
    var selectedBitmap: MutableStateFlow<ImageBitmap?>? = MutableStateFlow(null)
    var bitmapBase64String: MutableStateFlow<String> = MutableStateFlow("")

    var remarks: MutableStateFlow<String> = MutableStateFlow("")
    var areAllFieldsOkay: MutableStateFlow<Boolean> = MutableStateFlow(false)

    var submissionFlow: MutableStateFlow<Resource<Payment>?> = MutableStateFlow(null)
    var updatePaymentDateFlow: MutableStateFlow<Resource<PaymentScheduleDate>?> = MutableStateFlow(null)

    fun updateImageBox(context: Context, uri: Uri?) = viewModelScope.launch {
        selectedImageUri?.value = uri
        selectedBitmap?.value = ImageUtils.loadBitmapFromUri(context, uri)?.asImageBitmap()

        bitmapBase64String?.value = ImageUtils.encodeBitmapToBase64(selectedBitmap?.value?.asAndroidBitmap()!!)!!

        checkFieldsValidity()
    }

    fun updateRemarks(input: String) {
        remarks.value = input

        checkFieldsValidity()
    }

    fun checkFieldsValidity() {
        areAllFieldsOkay.value =
            selectedImageUri?.value != null &&
            selectedBitmap?.value != null &&
            (remarks?.value?.length in 1..257)
    }

    fun submitPaymentConfirmation() = viewModelScope.launch {
        submissionFlow.value = Resource.Loading
        val result = paymentRepository.addPayment(
            Payment(
                amount = loanTransactionItem.paymentPerSchedule,
                date = paymentScheduleDateItem.date,
                dateRequested = DateTimeUtils.formatToISO8601Date(Date()),
                paymentScheduleDateId = paymentScheduleDateItem.id,
                loanTransactionId = loanTransactionItem.id,
                borrowerProofImage = bitmapBase64String.value,
                borrowerRemarks = remarks.value
            )
        )
        submissionFlow.value = result
    }

    fun updatePaymentScheduleDateStatus(status: PaymentScheduleDateStatus) = viewModelScope.launch {
        updatePaymentDateFlow.value = Resource.Loading

        paymentScheduleDateItem.status = status.name.uppercase()
        val update = paymentScheduleDateItem

        val result = paymentScheduleRepository.updatePaymentDate(
            paymentScheduleDateId = paymentScheduleDateItem.id,
            update = update
        )

        updatePaymentDateFlow.value = result
    }
}