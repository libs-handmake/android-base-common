package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import common.hoangdz.lib.utils.error_handle.ErrorCollector
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun Modifier.gradientBackground(
    colors: List<Color>,
    angle: Float = 0f,
    tileMode: TileMode = TileMode.Clamp
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


@Composable
fun Modifier.shimmerEffect(
    shape: Shape = RectangleShape, colors: List<Color> = listOf(
        Color(0xFFB8B5B5),
        Color(0xFF8F8B8B),
        Color(0xFFB8B5B5),
    )
): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
        label = "startOffsetX"
    )
    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        ), shape
    ).onGloballyPositioned {
        size = it.size
    }
}


val SafeModifier: Modifier
    get() = Modifier.layout { measurable, constraints ->
        fun measure(eCount: Int = 0, e: Throwable? = null): MeasureResult {
            if (eCount == 3) {
                e?.let { ErrorCollector.collectError(it) }
                return try {
                    val placeable = measurable.measure(Constraints.fixed(200, 200))
                    layout(200, 200) {
                        kotlin.runCatching { placeable.placeRelative(0, 0) }
                    }
                } catch (e: Throwable) {
                    layout(0, 0) {}
                }
            }
            return try {
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {
                    kotlin.runCatching {
                        placeable.placeRelative(0, 0)
                    }
                }
            } catch (e: Throwable) {
                measure(eCount + 1, e)
            }
        }
        measure()
    }

@Composable
fun Modifier.safeArea() =
    windowInsetsPadding(WindowInsets.statusBars).windowInsetsPadding(WindowInsets.navigationBars)