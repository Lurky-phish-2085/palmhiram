package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp

data class PaymentScheduleDate(

    var date: Timestamp? = null,
    var settled: Boolean = false
)
