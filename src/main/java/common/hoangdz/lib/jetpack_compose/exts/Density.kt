package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

@Composable
fun Int.toComposeDP() = toComposeDP(LocalDensity.current)

fun Int.toComposeDP(density: Density) = with(density) {
    toDp()
}

@Composable
fun Dp.toPx() = with(LocalDensity.current) {
    toPx()
}

@Composable
fun Float.toComposeDP() = with(LocalDensity.current) {
    toDp()
}