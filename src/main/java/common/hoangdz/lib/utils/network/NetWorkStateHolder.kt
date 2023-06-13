package common.hoangdz.lib.utils.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by HoangDepTrai on 08, December, 2022 at 2:55 PM
 */
class NetWorkStateHolder {

    val networkAvailable
        get() = _isConnected.value ?: false

    private val _isConnected by lazy { MutableLiveData<Boolean>() }
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    fun updateNetworkState(state: Boolean) = _isConnected.postValue(state)

}