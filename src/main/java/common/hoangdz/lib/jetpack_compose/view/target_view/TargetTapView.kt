package common.hoangdz.lib.jetpack_compose.view.target_view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import common.hoangdz.lib.jetpack_compose.exts.SafeModifier
import common.hoangdz.lib.jetpack_compose.exts.clickableWithDebounce
import common.hoangdz.lib.jetpack_compose.exts.collectWhenResume
import common.hoangdz.lib.jetpack_compose.exts.scale
import common.hoangdz.lib.jetpack_compose.exts.toComposeDP
import kotlin.math.abs

@Composable
fun TargetTapView(
    vararg targetContent: TargetContent,
    skipWhenClickOutside: Boolean = false,
    onTargetClicked: ((TargetContent) -> Unit)? = null,
    onTargetClosed: (() -> Unit)? = null,
    onTargetCancel: (() -> Unit)? = null
) {

    val targetScope by remember {
        mutableStateOf(
            InstanceTargetScope(
                *targetContent,
                onTargetClicked = onTargetClicked,
                onTargetClosed = onTargetClosed,
                onTargetCancel = onTargetCancel
            )
        )
    }
    val targetAnim by targetScope.targetAnim.collectWhenResume()
    val animValue by animateFloatAsState(
        targetValue = targetAnim, label = "", animationSpec = tween(1500)
    ) {
        targetScope.reverseAnim()
    }
    val interactionSource = remember { MutableInteractionSource() }
    val showing by targetScope.showingContent.collectWhenResume()
    if (!showing) return
    val content = targetScope.currentContent
    Box(SafeModifier
        .clickableWithDebounce(
            interactionSource = interactionSource, indication = null
        ) {
            if (skipWhenClickOutside) targetScope.cancelTarget()
        }
        .onGloballyPositioned {
            targetScope.reverseAnim()
        }) {
        Canvas(modifier = SafeModifier.fillMaxSize(), onDraw = {
            val circlePath = Path().apply {
                if (content.shape is TargetShape.CircleShape) {
                    addOval(
                        Rect(
                            content.shape.position,
                            content.shape.size.width * (1.1f + .2f * animValue)
                        )
                    )
                } else if (content.shape is TargetShape.RoundedRectangleShape) {
                    addRoundRect(content.shape.rect.scale(1.1f + .1f * animValue))
                }
            }
            clipPath(circlePath, clipOp = ClipOp.Difference) {
                drawRect(SolidColor(Color.Black.copy(alpha = 0.6f)))
            }
        })
        val clickOffset = (content.shape.position - Offset(
            content.shape.size.width / 2f, content.shape.size.height / 2f
        )).run {
            Offset(abs(x), abs(y))
        }
        Box(modifier = SafeModifier
            .clickableWithDebounce(
                interactionSource = interactionSource, indication = null
            ) {
                targetScope.moveToNextTarget()
            }
            .padding(top = clickOffset.y.toComposeDP(), start = clickOffset.x.toComposeDP())
            .size(
                (content.shape.size.width).toComposeDP(),
                content.shape.size.height.toComposeDP()
            ))
        content.content.invoke(targetScope)
    }
}