package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import xyz.lurkyphish2085.capstone.palmhiram.utils.PaymentScheduleDateStatus
import java.util.Date

data class PaymentScheduleDate(
    @DocumentId
    val id: String = "",
    @ServerTimestamp
    val created: Timestamp? = null,

    val loanTransactionId: String = "",
    var date: Timestamp? = null,
    var status: String = "PENDING"
)
