package common.hoangdz.lib.utils.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import common.hoangdz.lib.jetpack_compose.exts.compareAndSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by HoangDepTrai on 08, December, 2022 at 2:55 PM
 */
class NetWorkStateHolder {

    val networkAvailable
        get() = _isConnected.value ?: false

    private val _isConnected by lazy { MutableLiveData<Boolean>() }
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    private val _isConnectState by lazy { MutableStateFlow(networkAvailable) }
    val isConnectState by lazy { _isConnectState.asStateFlow() }

    fun updateNetworkState(state: Boolean) {
        _isConnected.postValue(state)
        _isConnectState.compareAndSet(state)
    }

}