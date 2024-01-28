package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Payment(
    @DocumentId
    val id: String = "",
    @ServerTimestamp
    val created: Timestamp? = null,

    var dateRequested: String = "",
    var dateConfirmed: String = "",

    var paymentScheduleDateId: String = "",
    var loanTransactionId: String = "",

    var borrowerProofImage: String = "",
    var lenderProofImage: String = "",
    var borrowerRemarks: String = "",
    var lenderRemarks: String = "",
)
