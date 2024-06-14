package common.hoangdz.lib.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build

class NetworkHelper(private val context: Context) {

    val netWorkStateHolder by lazy { NetWorkStateHolder() }

    private fun updateState() {
        netWorkStateHolder.updateNetworkState(isConnected())
    }

    private var networkCallback: NetworkCallback? = null


    init {
        updateState()
    }

    fun startNetworkCallback() {
        val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)
            ?: return
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()
        networkCallback?.let { cm.unregisterNetworkCallback(it) }
        networkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                updateState()
            }

            override fun onLost(network: Network) {
                updateState()
            }
        }.also {
            cm.registerNetworkCallback(
                builder.build(), it
            )
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

}