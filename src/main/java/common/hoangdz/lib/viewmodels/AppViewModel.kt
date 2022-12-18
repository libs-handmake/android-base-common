package common.hoangdz.lib.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import common.hoangdz.lib.datasouce.Result
import common.hoangdz.lib.extensions.launchIO

/**
 * Created by HoangDepTrai on 22, November, 2022 at 3:28 PM
 */
open class AppViewModel(application: Application) : AndroidViewModel(application) {

    protected val context: Context by lazy { application }

    protected val _isLoading by lazy { MutableLiveData<Boolean>() }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected val _errorMessage by lazy { MutableLiveData<Result.Failure>() }
    val errorMessage: LiveData<Result.Failure>
        get() = _errorMessage

    fun <T> launchData(
        dataUpdate: MutableLiveData<T>? = null,
        progressUpdate: MutableLiveData<Boolean> = _isLoading,
        onSuccess: ((T) -> Unit)? = null,
        onFailure: (Result.Failure) -> Unit = { _errorMessage.postValue(it) },
        task: suspend () -> Result<T>
    ) {
        progressUpdate.postValue(true)
        viewModelScope.launchIO {
            val result = task()
            if (result is Result.Success<T>) {
                dataUpdate?.postValue(result.value)
                onSuccess?.invoke(result.value)
            } else if (result is Result.Failure) {
                onFailure(result)
            }
            progressUpdate.postValue(false)
        }
    }

}