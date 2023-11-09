package common.hoangdz.lib.jetpack_compose.view.target_view

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size

sealed class TargetShape(val size: Size, val position: Offset) {
    class CircleShape(size: Size, position: Offset) : TargetShape(size, position)
    class RoundedRectangleShape(size: Size, position: Offset, val radius: Int = 0) : TargetShape(
        size, position
    ) {
        val rect: RoundRect
            get() {
                return RoundRect(
                    left = position.x - size.width / 2f,
                    top = position.y - size.height / 2f,
                    right = position.x + size.width / 2f,
                    bottom = position.y + size.height / 2f,
                    cornerRadius = CornerRadius(radius * 1f, radius * 1f)
                )
            }
    }
}