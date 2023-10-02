package common.hoangdz.lib.jetpack_compose.view.target_view

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class TargetShape(val size: Size, val position: Offset) {
    class CircleShape(size: Size, position: Offset) : TargetShape(size, position)
    class RoundedRectangleShape(size: Size, position: Offset, val radius: Dp = 0.dp) : TargetShape(
        size, position
    )
}