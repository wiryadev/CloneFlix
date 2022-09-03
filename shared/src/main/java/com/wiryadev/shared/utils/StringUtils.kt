package com.wiryadev.shared.utils

import android.util.Patterns

object StringUtils {

    fun isEmailValid(input: CharSequence?): Boolean {
        return if (input.isNullOrEmpty()) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(input).matches()
        }
    }

}