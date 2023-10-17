package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.auth.FirebaseUser
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.Message
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPRequest

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(username: String, email: String, password: String): Resource<FirebaseUser>
    fun logout()
    suspend fun pingServer(): Resource<Message>
}