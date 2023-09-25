package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import java.util.regex.Pattern

class InputValidationUtil {

    companion object {
        // TODO: Improve email regex to check domains
        // accepts if there's characters b4 and after @
        const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
        // At least one uppercase letter, lowercase letters, and numbers
        const val PASSWORD_VALIDATION_REGEX = "^(?=.*[A-Z])(?=.*\\d).+\$"
        const val PASSWORD_VALID_LENGTH = 6

        fun validateEmail(email: String): Boolean {
            val pattern = Pattern.compile(EMAIL_VALIDATION_REGEX)
            val matcher = pattern.matcher(email)

            return matcher.matches()
        }

        fun validatePassword(password: String): Boolean {
            val pattern = Pattern.compile(PASSWORD_VALIDATION_REGEX)
            val matcher = pattern.matcher(password)

            return matcher.matches() && password.length >= PASSWORD_VALID_LENGTH
        }
    }
}