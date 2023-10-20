package xyz.lurkyphish2085.capstone.palmhiram.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class User(
    @DocumentId
    val id: String = "",

    var userId: String = "",
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var verified: Boolean = false,

    @ServerTimestamp
    val created: Timestamp? = null,
    @ServerTimestamp
    var modified: Timestamp? = null
)
