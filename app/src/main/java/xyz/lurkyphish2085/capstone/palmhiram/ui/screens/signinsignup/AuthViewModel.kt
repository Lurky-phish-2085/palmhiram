package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.signinsignup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.AuthRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.OTP
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.data.models.UserCredentials
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.Message
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPResponse
import xyz.lurkyphish2085.capstone.palmhiram.utils.Roles
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    val fields = UserCredentials("", "", "", "")
    var userDetails = User()

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
        Log.e("AAAAAAAAAAa", "sendVerificationEmail: AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa ")
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

    fun registerUserToDB(user: FirebaseUser) {
        viewModelScope.launch {
            repository.registerUserToDB(
                User(
                    userId = user.uid,
                    name = fields.displayName,
                    email = fields.email,
                    phone = fields.phone,
                    role = Roles.BORROWER,
                )
            )
        }
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