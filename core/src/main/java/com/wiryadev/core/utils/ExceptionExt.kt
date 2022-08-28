package com.wiryadev.core.utils

import android.content.Context
import com.wiryadev.core.exception.ApiErrorException
import com.wiryadev.core.exception.NoInternetConnectionException

fun Context.getErrorMessageByException(exception: Exception): String {
    return when(exception) {
        is NoInternetConnectionException -> getString(com.wiryadev.styling.R.string.message_error_no_internet)
        is ApiErrorException -> exception.message.orEmpty()
        else -> getString(com.wiryadev.styling.R.string.message_error_unknown)
    }
}