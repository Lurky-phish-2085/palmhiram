package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.auth.FirebaseUser
import xyz.lurkyphish2085.capstone.palmhiram.data.models.OTP
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.Message
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPResponse

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(username: String, email: String, password: String): Resource<FirebaseUser>
    fun logout()
    suspend fun pingServer(): Resource<Message>
    suspend fun sendOtpEmail(name: String, email: String): Resource<OTPResponse>
    suspend fun storeOtp(otp: OTP): Resource<OTP>
}