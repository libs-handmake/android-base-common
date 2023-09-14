package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun Int.toComposeDP() = with(LocalDensity.current) {
    toDp()
}