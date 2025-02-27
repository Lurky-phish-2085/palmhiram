package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.auth.FirebaseUser
import xyz.lurkyphish2085.capstone.palmhiram.data.models.OTP
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.data.models.VerificationCode
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.Message
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPResponse
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.SendEmailResponse
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.VerificationCodeResponse

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(username: String, email: String, password: String): Resource<FirebaseUser>
    fun logout()
    suspend fun pingServer(): Resource<Message>
    suspend fun sendOtpEmail(name: String, email: String): Resource<OTPResponse>
    suspend fun sendAccountVerificationEmail(name: String, email: String): Resource<SendEmailResponse>
    suspend fun sendAccountVerificationCodeEmail(name: String, email: String): Resource<VerificationCodeResponse>
    suspend fun storeOtp(otp: OTP): Resource<OTP>
    suspend fun retrievedValidOtp(email: String): Resource<OTP>
    suspend fun clearAllOtp(email: String)
    suspend fun storeVerificationCode(code: VerificationCode): Resource<VerificationCode>
    suspend fun retrievedValidVerificationCode(email: String): Resource<VerificationCode>
    suspend fun clearAllVerificationCode(email: String)
    suspend fun registerUserToDB(user: User): Resource<User>
    suspend fun checkExistingAccount(email: String): Resource<User>
    suspend fun verifyAccount(userId: String): Resource<User>
    suspend fun getUser(userId: String): Resource<User>
    suspend fun changePassword(oldPassword: String,newPassword: String): Resource<FirebaseUser>
}