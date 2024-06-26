package common.hoangdz.lib.extensions

import android.util.Log

/**
 * Created by HoangDepTrai on 18, July, 2022 at 11:21 AM
 */

infix fun Any.logError(message: Any?) {
    Log.e("Hoangdz logError" + javaClass.simpleName, "$message")
}

fun logError(message: Any?) {
    Log.e("Hoangdz logError", "$message")
}