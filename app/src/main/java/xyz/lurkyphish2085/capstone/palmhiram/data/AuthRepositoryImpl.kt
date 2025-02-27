package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import xyz.lurkyphish2085.capstone.palmhiram.data.models.OTP
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import xyz.lurkyphish2085.capstone.palmhiram.data.models.VerificationCode
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.Message
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPRequest
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.OTPResponse
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.SendEmailRequest
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.SendEmailResponse
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.VerificationCodeRequest
import xyz.lurkyphish2085.capstone.palmhiram.data.models.api.VerificationCodeResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthRepository {

    companion object {
        const val OTP_COLLECTIONS_PATH = "otp"
        const val USERS_COLLECTIONS_PATH = "users"
        const val VERIFICATION_CODES_PATH = "verification_codes"
    }

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun changePassword(oldPassword: String,newPassword: String): Resource<FirebaseUser> {
        return try {
            val credential = EmailAuthProvider.getCredential(currentUser?.email!!, oldPassword)
            currentUser?.reauthenticate(credential)?.await()
            val result = currentUser?.updatePassword(newPassword)?.await()
            Resource.Success(currentUser!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signup(
        username: String,
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
            )?.await()

            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun pingServer(): Resource<Message> {
        return try {
            val response = RetrofitInstance.api.getPing()
            Resource.Success(response.body()!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun sendOtpEmail(name: String, email: String): Resource<OTPResponse> {
        return try {
            val response = RetrofitInstance.api.generateOtp(OTPRequest(name, email))
            Resource.Success(response.body()!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun sendAccountVerificationEmail(
        name: String,
        email: String
    ): Resource<SendEmailResponse> {
        return try {
            val response = RetrofitInstance.api.sendVerificationEmail(SendEmailRequest(name, email))
            Resource.Success(response.body()!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun sendAccountVerificationCodeEmail(
        name: String,
        email: String
    ): Resource<VerificationCodeResponse> {
        return try {
            val response = RetrofitInstance.api.sendVerificationCodeEmail(VerificationCodeRequest(name, email))
            Resource.Success(response.body()!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun storeOtp(otp: OTP): Resource<OTP> {
        return try {
            val result = firebaseFirestore.collection(OTP_COLLECTIONS_PATH).add(otp).await()
            val retrievedItem = result.get().await()
            Resource.Success(retrievedItem.toObject(OTP::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrievedValidOtp(email: String): Resource<OTP> {
        return try {
            val document = firebaseFirestore.collection(OTP_COLLECTIONS_PATH)
                .whereEqualTo("email", email)
                .orderBy("created", Query.Direction.DESCENDING)
                .limit(1)
                .get().await()

            val result = document.documents.get(0).toObject(OTP::class.java)
            Resource.Success(result!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun clearAllOtp(email: String) {
        val docuemets = firebaseFirestore.collection(OTP_COLLECTIONS_PATH)
            .whereEqualTo("email", email)
            .get().await()

        docuemets.forEach {
            firebaseFirestore.collection(OTP_COLLECTIONS_PATH)
                .document(it.id).delete().await()
        }
    }

    override suspend fun storeVerificationCode(code: VerificationCode): Resource<VerificationCode> {
        return try {
            val result = firebaseFirestore.collection(VERIFICATION_CODES_PATH).add(code).await()
            val retrievedItem = result.get().await()
            Resource.Success(retrievedItem.toObject(VerificationCode::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrievedValidVerificationCode(email: String): Resource<VerificationCode> {
        return try {
            val document = firebaseFirestore.collection(VERIFICATION_CODES_PATH)
                .whereEqualTo("email", email)
                .orderBy("created", Query.Direction.DESCENDING)
                .limit(1)
                .get().await()

            val result = document.documents.get(0).toObject(VerificationCode::class.java)
            Resource.Success(result!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun clearAllVerificationCode(email: String) {
        val docuemets = firebaseFirestore.collection(VERIFICATION_CODES_PATH)
            .whereEqualTo("email", email)
            .get().await()

        docuemets.forEach {
            firebaseFirestore.collection(VERIFICATION_CODES_PATH)
                .document(it.id).delete().await()
        }
    }

    override suspend fun registerUserToDB(user: User): Resource<User> {
        return try {
            val document = firebaseFirestore.collection(USERS_COLLECTIONS_PATH)
                .add(user)
                .await()

            val result = document.get().await()

            Resource.Success(result.toObject(User::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun checkExistingAccount(email: String): Resource<User> {
        return try {
            val document = firebaseFirestore.collection(USERS_COLLECTIONS_PATH)
                .whereEqualTo("email", email)
                .get()
                .await()

            val result = document.documents.get(0)

            Resource.Success(result.toObject(User::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun verifyAccount(userId: String): Resource<User> {
        return try {
            val document = firebaseFirestore.collection(USERS_COLLECTIONS_PATH)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val queriedUserData = document.documents.get(0)
            val queriedUserDataObject = queriedUserData.toObject(User::class.java)

            queriedUserDataObject?.verified = true

            val alteredDocument = firebaseFirestore.collection(USERS_COLLECTIONS_PATH)
                .add(queriedUserDataObject!!)
                .await()

            val result = alteredDocument.get().await()

            Resource.Success(result.toObject(User::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getUser(userId: String): Resource<User> {
        return try {
            val document = firebaseFirestore.collection(USERS_COLLECTIONS_PATH)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val result = document.documents.get(0)

            Resource.Success(result.toObject(User::class.java)!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}