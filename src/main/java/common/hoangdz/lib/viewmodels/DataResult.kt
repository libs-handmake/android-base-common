package common.hoangdz.lib.viewmodels

class DataResult<out T>(val state: DataState, val value: T? = null) {
    enum class DataState {
        IDLE, LOADING, LOADED, ERROR
    }
}