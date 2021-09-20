package ir.omidtaheri.domain.helper

import ir.omidtaheri.domain.R
import java.util.concurrent.TimeoutException

fun getStringErrorMessage(throwable: Throwable): String {

    return when (throwable) {

        is IllegalAccessException -> "Wrong Argument"
        is TimeoutException -> "TimeOut"
        else -> "Error"
    }
}

fun getResErrorMessage(throwable: Throwable): Int {

    return when (throwable) {
        is IllegalAccessException -> R.string.IllegalAccessException
        is TimeoutException -> R.string.timeout_error
        else -> R.string.general_error
    }
}
