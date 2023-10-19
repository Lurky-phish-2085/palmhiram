package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class User(
    @DocumentId
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val verified: Boolean,

    @ServerTimestamp
    val created: Timestamp? = null,
    @ServerTimestamp
    var modified: Timestamp? = null
) {
    constructor(): this("", "", "", "", false, null, null)
}
