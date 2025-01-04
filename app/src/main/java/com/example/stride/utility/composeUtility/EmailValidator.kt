package com.example.stride.utility.composeUtility

import android.text.TextUtils
import android.util.Patterns

fun isValidEmail(target: CharSequence?): Any {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidMobile(): Boolean {
    val mobileRegex = "^[6-9]\\d{9}$"
    return this.isNotBlank() && Regex(mobileRegex).matches(this)
}