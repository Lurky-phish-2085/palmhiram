package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import java.util.regex.Pattern

class InputValidationUtils {

    companion object {
        // TODO: Improve email regex to check domains
        // accepts if there's characters b4 and after @
        const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"

        // At least one uppercase letter, lowercase letters, and numbers
        const val PASSWORD_VALIDATION_REGEX = "^(?=.*[A-Z])(?=.*\\d).+\$"
        const val PASSWORD_VALID_LENGTH = 6

        // https://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters
        const val NAME_REGEX = "^[\\p{L}\\p{M} .'-]+\$"
        const val  PHILIPPINE_PHONE_NUMBER_REGEX = "^(\\+?63|0)?[9]\\d{9}$|^\\+?63[2-9]\\d{8}$"

        const val NUMBERS_ONLY_REGEX = "\\d+$"
        const val NUMBERS_AND_POINTS_ONLY_REGEX = "^\\d*\\.?\\d+\$"

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

        fun validatePhoneNumber(phoneNumber: String): Boolean {
            val pattern = Pattern.compile(PHILIPPINE_PHONE_NUMBER_REGEX)
            val matcher = pattern.matcher(phoneNumber)

            return matcher.matches()
        }

        fun validateNumeric(input: String): Boolean {
            val pattern = Pattern.compile(NUMBERS_ONLY_REGEX)
            val matcher = pattern.matcher(input)

            return matcher.matches()
        }

        fun validateNumericWithPoints(input: String): Boolean {
            val pattern = Pattern.compile(NUMBERS_AND_POINTS_ONLY_REGEX)
            val matcher = pattern.matcher(input)

            return matcher.matches()
        }
    }
}