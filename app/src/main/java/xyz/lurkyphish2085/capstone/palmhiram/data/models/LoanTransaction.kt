package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class LoanTransaction(
    @DocumentId
    val id: String = "",
    val borrowerId: String = "",

    var totalBalance: Long = 0L,
    var totalPrincipalBalance: Long = 0L,
    var totalInterestBalance: Long = 0L,

    var totalPayment: Long = 0L,
    var principalAmount: Long = 0L,
    var interestRateInPercentage: Int = 0,
    var timePeriodInYears: Float = 0.0F,
    var startDate: Timestamp? = null,
    var EndDate: Timestamp? = null,
    var status: String = "",

    @ServerTimestamp
    val created: Timestamp? = null,
    @ServerTimestamp
    var modified: Timestamp? = null,
)
