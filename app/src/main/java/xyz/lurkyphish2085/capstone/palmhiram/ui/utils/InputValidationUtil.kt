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

        // https://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters
        const val NAME_REGEX = "^[\\p{L}\\p{M} .'-]+\$"

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

        fun validateName(name: String): Boolean {
            val pattern = Pattern.compile(NAME_REGEX)
            val matcher = pattern.matcher(name)

            return matcher.matches()
        }
    }
}