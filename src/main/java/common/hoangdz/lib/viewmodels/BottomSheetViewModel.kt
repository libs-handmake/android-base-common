package common.hoangdz.lib.viewmodels

import android.app.Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BottomSheetViewModel(application: Application) : AppViewModel(application) {
    private val _sheetState by lazy { MutableStateFlow(SheetState.HIDDEN) }
    val sheetState by lazy { _sheetState.asStateFlow() }

    fun toggle() {
        _sheetState.value =
            if (sheetState.value == SheetState.HIDDEN) SheetState.EXPAND else SheetState.HIDDEN
    }
}

enum class SheetState {
    HIDDEN, EXPAND
}