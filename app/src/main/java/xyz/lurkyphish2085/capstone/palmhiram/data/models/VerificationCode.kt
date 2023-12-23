package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class VerificationCode(
    val code: String,
    val email: String,
    @ServerTimestamp
    val created: Timestamp? = null
) {
    constructor(): this("", "", null)
}
