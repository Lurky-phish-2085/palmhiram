package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.home

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.PaymentScheduleRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import xyz.lurkyphish2085.capstone.palmhiram.data.models.Payment
import xyz.lurkyphish2085.capstone.palmhiram.data.models.PaymentScheduleDate
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.DateTimeUtils
import xyz.lurkyphish2085.capstone.palmhiram.ui.utils.ImageUtils
import xyz.lurkyphish2085.capstone.palmhiram.utils.LoanTransactionStatus
import xyz.lurkyphish2085.capstone.palmhiram.utils.PaymentScheduleDateStatus
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class LenderConfirmLoanPaymentViewModel @Inject constructor(
    private val loanTransactionRepository: LoanTransactionRepository,
    private val paymentRepository: PaymentRepository,
    private val paymentScheduleRepository: PaymentScheduleRepository
): ViewModel() {

    val payments: Flow<List<Payment>>
        get() = paymentRepository.payments

    var paymentsForSelectedScheduleDate: MutableStateFlow<List<Payment>?>? = MutableStateFlow(null)

    var paymentScheduleDateItem: PaymentScheduleDate = PaymentScheduleDate()
    var loanTransactionItem: LoanTransaction = LoanTransaction()
    var paymentItem: Payment = Payment()

    var selectedImageUri: MutableStateFlow<Uri?>? = MutableStateFlow(null)
    var selectedBitmap: MutableStateFlow<ImageBitmap?>? = MutableStateFlow(null)
    var bitmapBase64String: MutableStateFlow<String> = MutableStateFlow("")

    var remarks: MutableStateFlow<String> = MutableStateFlow("")
    var areAllFieldsOkay: MutableStateFlow<Boolean> = MutableStateFlow(false)

    var submissionFlow: MutableStateFlow<Resource<Payment>?> = MutableStateFlow(null)
    var updatePaymentDateFlow: MutableStateFlow<Resource<PaymentScheduleDate>?> = MutableStateFlow(null)
    var updateLoanTransactionFlow: MutableStateFlow<Resource<LoanTransaction>?> = MutableStateFlow(null)
    var declinePaymentDateFlow: MutableStateFlow<Resource<PaymentScheduleDate>?> = MutableStateFlow(null)

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

        paymentItem.lenderProofImage = bitmapBase64String.value
        paymentItem.lenderRemarks = remarks.value
        paymentItem.dateConfirmed = DateTimeUtils.formatToISO8601Date(Date())
        paymentItem.hasConfirmed = true


        val result = paymentRepository.updatePayment(paymentItem.id, paymentItem)

        submissionFlow.value = result
    }

    fun declinePaymentConfirmation() = viewModelScope.launch {
        declinePaymentDateFlow.value = Resource.Loading

        paymentScheduleDateItem.status = PaymentScheduleDateStatus.PENDING.name
        val result = paymentScheduleRepository.updatePaymentDate(paymentScheduleDateItem.id, paymentScheduleDateItem)

        declinePaymentDateFlow.value = result
    }

    fun updatePaymentScheduleDateStatus(status: PaymentScheduleDateStatus) = viewModelScope.launch {
        updatePaymentDateFlow.value = Resource.Loading

        paymentScheduleDateItem.status = status.name.uppercase()
        val update = paymentScheduleDateItem

        val result = paymentScheduleRepository.updatePaymentDate(
            paymentScheduleDateId = paymentScheduleDateItem.id,
            update = update
        )

        collectPaymentsForSelectedDate()
        updatePaymentDateFlow.value = result
    }

    fun collectPaymentsForSelectedDate() = viewModelScope.launch {
        payments.collectLatest {
            val collectedValues = it.filter { payment ->
                payment.paymentScheduleDateId == paymentScheduleDateItem.id
            }

            paymentsForSelectedScheduleDate?.value = collectedValues
        }
    }
    fun updateLoanTransaction() = viewModelScope.launch {
        updateLoanTransactionFlow.value = Resource.Loading

        loanTransactionItem.totalBalance = loanTransactionItem.totalBalance - paymentItem.amount

        if (loanTransactionItem.totalBalance <= 0) {
            loanTransactionItem.status = LoanTransactionStatus.SETTLED.name
            loanTransactionItem.totalBalance = 0L
        }

        val result = loanTransactionRepository.updateLoanTransaction(loanTransactionItem.id, loanTransactionItem)

        updateLoanTransactionFlow.value = result
    }

    init {
        collectPaymentsForSelectedDate()
    }
}