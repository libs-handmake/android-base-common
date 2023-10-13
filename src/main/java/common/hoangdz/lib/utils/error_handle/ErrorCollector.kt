package common.hoangdz.lib.utils.error_handle

object ErrorCollector {
    var onNewError: ((Throwable) -> Unit)? = null

    fun collectError(e: Throwable) {
        onNewError?.invoke(e)
    }
}