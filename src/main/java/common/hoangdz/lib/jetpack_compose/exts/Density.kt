package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Int.toComposeDP() = with(LocalDensity.current) {
    toDp()
}

@Composable
fun Dp.toPx()=with(LocalDensity.current) {
    toPx()
}

@Composable
fun Float.toComposeDP() = with(LocalDensity.current) {
    toDp()
}