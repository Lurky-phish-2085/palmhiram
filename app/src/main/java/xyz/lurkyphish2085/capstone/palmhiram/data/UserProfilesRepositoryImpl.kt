package xyz.lurkyphish2085.capstone.palmhiram.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User
import javax.inject.Inject

class UserProfilesRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : UserProfilesRepository {

    companion object {
        const val USERS_COLLECTIONS_PATH = "users"
    }

    override val userProfiles: Flow<List<User>>
        get() = firebaseFirestore.collection(USERS_COLLECTIONS_PATH)
            .snapshots().map { snapshots ->
                snapshots.toObjects(User::class.java)
            }
}