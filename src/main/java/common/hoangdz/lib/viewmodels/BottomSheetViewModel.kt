package common.hoangdz.lib.viewmodels

import android.app.Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BottomSheetViewModel(application: Application) : AppViewModel(application) {
    private val _sheetState by lazy { MutableStateFlow(SheetState.HIDDEN) }
    val sheetState by lazy { _sheetState.asStateFlow() }
}

enum class SheetState {
    HIDDEN, EXPAND
}