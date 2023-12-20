package xyz.lurkyphish2085.capstone.palmhiram.data

import kotlinx.coroutines.flow.Flow
import xyz.lurkyphish2085.capstone.palmhiram.data.models.User

interface UserProfilesRepository {

    val userProfiles: Flow<List<User>>
}