package common.hoangdz.lib.jetpack_compose.view.local_composition

import androidx.compose.runtime.compositionLocalOf

data class ScreenInformation(val name: String)

val LocalScreenInfo = compositionLocalOf { ScreenInformation("Nowhere screen :(((") }