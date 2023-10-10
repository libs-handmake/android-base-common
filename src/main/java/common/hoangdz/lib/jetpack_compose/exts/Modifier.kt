package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.Role
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun Modifier.gradientBackground(
    colors: List<Color>, angle: Float = 0f, tileMode: TileMode = TileMode.Clamp
) = this.then(Modifier.drawBehind {
    val angleRad = angle / 180f * PI
    val x = cos(angleRad).toFloat() //Fractional x
    val y = sin(angleRad).toFloat() //Fractional y

    val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
    val offset = center + Offset(x * radius, y * radius)

    val exactOffset = Offset(
        x = min(offset.x.coerceAtLeast(0f), size.width),
        y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
    )

    drawRect(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(size.width, size.height) - exactOffset,
            end = exactOffset,
        ), size = size
    )
})

fun Modifier.invisible(): Modifier {
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {

        }
    }
}

@Composable
fun Modifier.clickableWithDebounce(
    debounce: Long = 500L,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier {
    var lastClick by remember {
        mutableLongStateOf(0L)
    }
    return clickable(
        enabled, onClickLabel, role
    ) {
        if (System.currentTimeMillis() - lastClick < debounce || !enabled) return@clickable
        lastClick = System.currentTimeMillis()
        onClick()
    }
}

@Composable
fun Modifier.clickableWithDebounce(
    debounce: Long = 500L,
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier {
    var lastClick by remember {
        mutableLongStateOf(0L)
    }
    if (enabled) return clickable(
        interactionSource, indication, enabled, onClickLabel, role
    ) {
        if (System.currentTimeMillis() - lastClick < debounce || !enabled) return@clickable
        lastClick = System.currentTimeMillis()
        onClick()
    }
    return this
}