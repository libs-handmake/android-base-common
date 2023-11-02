package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

fun Offset.rotateDeg(deg: Float): Offset {
    val sin = sin(Math.toRadians(deg * 1.0)).toFloat()
    val cos = cos(Math.toRadians(deg * 1.0)).toFloat()
    return Offset(x * cos - y * sin, x * sin + y * cos)
}