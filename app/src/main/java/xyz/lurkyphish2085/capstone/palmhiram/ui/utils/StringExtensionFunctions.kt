package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern


fun String.capitalized(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}
fun String.extractNumericValue(): String {
    val inputString = this

    // Define a pattern to match the numeric value

    // Define a pattern to match the numeric value
    val pattern: Pattern = Pattern.compile("\\d+\\.\\d+")

    // Create a matcher for the input string

    // Create a matcher for the input string
    val matcher: Matcher = pattern.matcher(inputString)

    // Check if the pattern is found

    // Check if the pattern is found
    if (!matcher.find()) {
        return "0.00"
    }

    // Extract the numeric value from the match
    val numericValue = matcher.group()

    return numericValue
}