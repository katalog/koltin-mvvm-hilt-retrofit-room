package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Util

import java.util.regex.Pattern

object RegexUtil {
    private val URL_PATTERN: Pattern = Pattern.compile(
        "((http|https)://)(www.)?"
    + "[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]"
    + "{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)"
    )

    fun isValidUrl(url:String): Boolean {
        return URL_PATTERN.matcher(url).matches()
    }
}