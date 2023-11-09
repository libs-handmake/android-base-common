package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.ui.geometry.RoundRect

fun RoundRect.scale(scalar: Float): RoundRect {
    val x = left + (width) / 2f
    val y = top + (height) / 2f
    val w = width * scalar
    val h = height * scalar
    return RoundRect(
        left = x - w / 2f,
        top = y - h / 2f,
        right = x + w / 2f,
        bottom = y + h / 2f,
        bottomLeftCornerRadius = bottomLeftCornerRadius,
        bottomRightCornerRadius = bottomRightCornerRadius,
        topLeftCornerRadius = topLeftCornerRadius,
        topRightCornerRadius = topRightCornerRadius
    )
}