package common.hoangdz.lib.viewmodels

import android.app.Application
import androidx.annotation.CallSuper
import common.hoangdz.lib.jetpack_compose.exts.compareAndSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class DialogViewModel(application: Application) : AppViewModel(application) {
    private val _dialogState by lazy { MutableStateFlow(false) }
    val dialogState by lazy { _dialogState.asStateFlow() }

    @CallSuper
    open fun showDialog() = _dialogState.compareAndSet(true)

    @CallSuper
    open fun dismissDialog() = _dialogState.compareAndSet(false)

}