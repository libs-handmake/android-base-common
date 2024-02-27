package common.hoangdz.lib.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import common.hoangdz.lib.datasouce.Source
import common.hoangdz.lib.extensions.launchIO
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by HoangDepTrai on 22, November, 2022 at 3:28 PM
 */
open class AppViewModel(application: Application) : AndroidViewModel(application) {

    protected val context: Context by lazy { application }

    protected val _isLoading by lazy { MutableLiveData<Boolean>() }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected val _errorMessage by lazy { MutableLiveData<Source.Failure>() }
    val errorMessage: LiveData<Source.Failure>
        get() = _errorMessage


    fun <T> launchData(
        dataUpdate: MutableStateFlow<DataResult<T>>? = null,
        onSuccess: ((T) -> Unit)? = null,
        onFailure: ((Source.Failure) -> Unit)? = null,
        task: suspend () -> Source<T>
    ) {
        if (dataUpdate?.value?.state != DataResult.DataState.LOADED) dataUpdate?.value =
            DataResult(DataResult.DataState.LOADING)
        viewModelScope.launchIO {
            try {
                val result = task()
                if (result is Source.Success<T>) {
                    dataUpdate?.value = DataResult(DataResult.DataState.LOADED, result.value)
                    onSuccess?.invoke(result.value)
                } else if (result is Source.Failure) {
                    dataUpdate?.value = DataResult(DataResult.DataState.ERROR)
                    onFailure?.invoke(result)
                }
            } catch (e: Throwable) {
                dataUpdate?.value = DataResult(DataResult.DataState.ERROR)
                onFailure?.invoke(Source.Failure())
            }
        }
    }

    @Deprecated(
        "", replaceWith = ReplaceWith(
            "launchData(dataUpdate = ,onSuccess = onSuccess,onFailure = onFailure,task)"
        )
    )
    fun <T> launchData(
        dataUpdate: MutableLiveData<T>? = null,
        progressUpdate: MutableLiveData<Boolean>? = _isLoading,
        onSuccess: ((T) -> Unit)? = null,
        onFailure: (Source.Failure) -> Unit = { _errorMessage.postValue(it) },
        task: suspend () -> Source<T>
    ) {
        progressUpdate?.postValue(true)
        viewModelScope.launchIO {
            val result = task()
            if (result is Source.Success<T>) {
                dataUpdate?.postValue(result.value)
                onSuccess?.invoke(result.value)
            } else if (result is Source.Failure) {
                onFailure(result)
            }
            progressUpdate?.postValue(false)
        }
    }

}