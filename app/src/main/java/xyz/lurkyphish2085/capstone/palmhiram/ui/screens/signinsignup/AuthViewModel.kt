package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.oAuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.AuthRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.OTP
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.data.models.UserCredentials
import xyz.lurkyphish2085.capstone.palmhiram.data.models.VerificationCode
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.Message
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPResponse
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.VerificationCodeResponse
import xyz.lurkyphish2085.capstone.palmhiram.utils.UserRoles
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    val fields = UserCredentials("", "", "", "")
    var userDetails = User()

    var changePasswordFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)

    fun changePassword(oldPassword: String, password: String) = viewModelScope.launch {
        changePasswordFlow.value = Resource.Loading
        val result = repository.changePassword(oldPassword = oldPassword, newPassword = password)
        changePasswordFlow.value = result
    }

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    private val _pingMessageFlow = MutableStateFlow<Resource<Message>?>(null)
    val pingMessageFlow: StateFlow<Resource<Message>?> = _pingMessageFlow

    private val _otpResponseFlow = MutableStateFlow<Resource<OTPResponse>?>(null)
    val otpResponseFlow: StateFlow<Resource<OTPResponse>?> = _otpResponseFlow

    private val _otpFlow = MutableStateFlow<Resource<OTP>?>(null)
    val otpFlow: StateFlow<Resource<OTP>?> = _otpFlow

    private val _retrievedOtpFlow = MutableStateFlow<Resource<OTP>?>(null)
    val retrievedOtpFlow: StateFlow<Resource<OTP>?> = _retrievedOtpFlow

    private val _verificationCodeResponseFlow = MutableStateFlow<Resource<VerificationCodeResponse>?>(null)
    val verificationCodeResponseFlow: StateFlow<Resource<VerificationCodeResponse>?> = _verificationCodeResponseFlow

    private val _verificationCodeFlow = MutableStateFlow<Resource<VerificationCode>?>(null)
    val verificationCodeFlow: StateFlow<Resource<VerificationCode>?> = _verificationCodeFlow

    private val _retrievedVerificationFlow = MutableStateFlow<Resource<VerificationCode>?>(null)
    val retrievedVerificationCodeFlow: StateFlow<Resource<VerificationCode>?> = _retrievedVerificationFlow

    private var _verifiedUserConfirmationFlow = MutableStateFlow<Resource<User>?>(null)
    val verifiedUserConfirmationFlow: StateFlow<Resource<User>?> = _verifiedUserConfirmationFlow

    private val _emailInUseFlow = MutableStateFlow<Resource<User>?>(null)
    val emailInUseFlow: StateFlow<Resource<User>?> = _emailInUseFlow

    private val _retrievingUserFlow = MutableStateFlow<Resource<User>?>(null)
    val retrievingUserFlow: StateFlow<Resource<User>?> = _retrievingUserFlow

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }

    fun signup(username: String, email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup(username, email, password)
        _signupFlow.value = result
    }
    fun signup() {
        signup(fields.displayName, fields.email, fields.pass)
    }

    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }

    fun pingServer() = viewModelScope.launch {
        _pingMessageFlow.value = Resource.Loading
        val result = repository.pingServer()
        _pingMessageFlow.value = result
    }

    fun sendOtpEmail() = viewModelScope.launch {
        _otpResponseFlow.value = Resource.Loading
        val result = repository.sendOtpEmail(fields.displayName, fields.email)
        _otpResponseFlow.value = result
    }

    fun sendVerificationEmail() = viewModelScope.launch {
        repository.sendAccountVerificationEmail(fields.displayName, fields.email)
    }

    fun sendVerificationCodeEmail(displayName: String = "", email: String = "") = viewModelScope.launch {
        _verificationCodeResponseFlow.value = Resource.Loading

       val result = repository.sendAccountVerificationCodeEmail(
           if (displayName.isBlank()) fields.displayName else displayName,
           if (email.isBlank()) fields.email else email,
       )

        Log.e("CALLED", "AUTHVIEWMODEL: SENDING VER CODE EMAIL")
        _verificationCodeResponseFlow.value = result
    }

    fun storeOtp(code: String) = viewModelScope.launch {
        _otpFlow.value = Resource.Loading
        val result = repository.storeOtp(OTP(code, fields.email))
        _otpFlow.value = result
    }

    fun retrieveValidOtp() = viewModelScope.launch {
        _retrievedOtpFlow.value = Resource.Loading
        val result = repository.retrievedValidOtp(fields.email)
        _retrievedOtpFlow.value = result
    }

    fun clearAllOtp(email: String) {
        viewModelScope.launch {
            repository.clearAllOtp(email)
        }
    }

    fun storeVerificationCode(code: String) = viewModelScope.launch {
        _verificationCodeFlow.value = Resource.Loading
        val result = repository.storeVerificationCode(VerificationCode(code, fields.email))
        Log.e("CALLED", "AUTHVIEWMODEL: STORING VER CODE EMAIL")
        _verificationCodeFlow.value = result
    }

    fun retrieveValidVerificationCode(email: String = "") = viewModelScope.launch {
        _retrievedVerificationFlow.value = Resource.Loading
        val result = repository.retrievedValidVerificationCode(
            if (email.isBlank()) fields.email else email
        )
        _retrievedVerificationFlow.value = result
    }

    fun clearAllVerificationCodes(email: String) {
        viewModelScope.launch {
            repository.clearAllVerificationCode(email)
        }
    }

    fun registerUserToDB(user: FirebaseUser) {
        viewModelScope.launch {
            repository.registerUserToDB(
                User(
                    userId = user.uid,
                    name = fields.displayName,
                    email = fields.email,
                    phone = fields.phone,
                    role = UserRoles.BORROWER.toString(),
                )
            )
        }
    }

    fun verifyUser(userId: String) = viewModelScope.launch {
        _verifiedUserConfirmationFlow.value = Resource.Loading
        val result = repository.verifyAccount(userId)
        _verifiedUserConfirmationFlow.value = result
    }

    fun checkIfEmailInUse() = viewModelScope.launch {
        _emailInUseFlow.value = Resource.Loading
        val result = repository.checkExistingAccount(fields.email)
        _emailInUseFlow.value = result
    }

    fun checkIfUserIsVerified() = viewModelScope.launch {
        _retrievingUserFlow.value = Resource.Loading
        val result = repository.getUser(currentUser?.uid!!)
        _retrievingUserFlow.value = result
    }
}