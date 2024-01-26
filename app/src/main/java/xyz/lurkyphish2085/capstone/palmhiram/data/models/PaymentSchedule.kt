package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class PaymentSchedule(
    @DocumentId
    val id: String = "",
    @ServerTimestamp
    val created: Timestamp? = null,
    @ServerTimestamp
    var modified: Timestamp? = null,

    val loanTransactionId: String = "",
    val paymentDates: List<PaymentScheduleDate> = listOf(PaymentScheduleDate())
)
