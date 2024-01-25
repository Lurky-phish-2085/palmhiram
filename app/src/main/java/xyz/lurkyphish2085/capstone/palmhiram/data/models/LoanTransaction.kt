package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

// NOTE: ALL MONETARY VALUES AER IN CENTS

data class LoanTransaction(
    @DocumentId
    val id: String = "",
    val borrowerId: String = "",
    val borrowerName: String = "",

    var totalBalance: Long = 0L,
    var totalPrincipalBalance: Long = 0L,
    var totalInterestBalance: Long = 0L,

    var totalPayment: Long = 0L,
    var principalAmount: Long = 0L,
    var interestRateInPercentage: Int = 0,
    var startDate: Timestamp? = null,
    var endDate: Timestamp? = null,
    var status: String = "",

    var repaymentFrequency: String = "",

    var remarks: String = "",

    @ServerTimestamp
    val created: Timestamp? = null,
    @ServerTimestamp
    var modified: Timestamp? = null,
)
