package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntSize

val LayoutCoordinates.actualRootPosition
    get() = positionInRoot() + Offset(
        size.width.toFloat() / 2f, size.height.toFloat() / 2f
    )

fun IntSize.toFloatSize() = Size(width.toFloat(), height.toFloat())