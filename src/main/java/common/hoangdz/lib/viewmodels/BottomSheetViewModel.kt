package common.hoangdz.lib.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import common.hoangdz.lib.extensions.launchIO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive

open class BottomSheetViewModel(application: Application) : AppViewModel(application) {
    private val _sheetState by lazy { MutableStateFlow(SheetState.HIDDEN) }
    val sheetState by lazy { _sheetState.asStateFlow() }

    private val _sheetPG by lazy { MutableStateFlow(0f) }
    val sheetPG by lazy { _sheetPG.asStateFlow() }

    private var animateJob: Job? = null


    fun expand() {
        _sheetState.value = SheetState.EXPAND
        animateTo(1f)
    }

    fun updateDragProgress(pg: Float) {
        _sheetPG.value = pg
    }

    fun animateTo(pg: Float) {
        animateJob?.cancel()
        animateJob = viewModelScope.launchIO {
            val delta = .1f
            if (_sheetPG.value < pg) {
                while (_sheetPG.value < pg && isActive) {
                    if (_sheetPG.value + delta > pg) _sheetPG.value =
                        pg else _sheetPG.value += delta
                    delay(10L)
                }
            } else if (_sheetPG.value > pg) {
                while (_sheetPG.value > pg && isActive) {
                    if (_sheetPG.value - delta < pg) _sheetPG.value =
                        pg else _sheetPG.value -= delta
                    delay(10L)
                }
            }
        }
    }

    fun hide() {
        _sheetState.value = SheetState.HIDDEN
        animateTo(0f)
    }

    fun toggle() {
        if (sheetState.value == SheetState.HIDDEN) expand() else hide()
    }
}

enum class SheetState {
    HIDDEN, EXPAND
}