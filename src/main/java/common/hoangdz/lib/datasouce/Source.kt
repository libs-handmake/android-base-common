package common.hoangdz.lib.datasouce

import androidx.annotation.StringRes

/**
 * Created by HoangDepTrai on 22, November, 2022 at 3:13 PM
 */
sealed class Source<out T> {

    class Success<T>(val value: T) : Source<T>()

    class Failure(
        @StringRes val mgsRes: Int? = null,
        val mgsStr: String = "",
        val errorType: ErrorType
    ) : Source<Nothing>()

    enum class ErrorType {
        SERVER,
        ERROR_NET_WORK,
        OTHER
    }
}


