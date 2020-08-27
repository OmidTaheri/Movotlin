package ir.omidtaheri.domain.helper

import ir.omidtaheri.domain.R
import java.util.concurrent.TimeoutException

fun getStringErrorMessage(throwable: Throwable): String {
    val message = when (throwable) {

        is IllegalAccessException -> "Wrong Argument"
        is TimeoutException -> "TimeOut"
        else -> "Error"
    }

    return message
}

fun getResErrorMessage(throwable: Throwable): Int {

    val message = when (throwable) {
        is IllegalAccessException -> R.string.IllegalAccessException
        is TimeoutException -> R.string.timeout_error
        else -> R.string.general_error
    }

    return message
}
