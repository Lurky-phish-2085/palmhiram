package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import xyz.lurkyphish2085.capstone.palmhiram.data.models.User

class SearchUtils {

    companion object {

        fun filterUserListByName(name: String, list: List<User>): List<User> {
            return list.filter { it.name.contains(name) }
        }
    }
}