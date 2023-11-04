package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import java.util.Locale

fun String.capitalized(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}
