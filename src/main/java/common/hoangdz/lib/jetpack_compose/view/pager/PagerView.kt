package common.hoangdz.lib.jetpack_compose.view.pager

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import common.hoangdz.lib.jetpack_compose.exts.SafeModifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import common.hoangdz.lib.extensions.logError
import ir.kaaveh.sdpcompose.sdp
import kotlin.math.abs

@Composable
fun PagerView(
    vararg pages: @Composable (Modifier) -> Unit,
    selected: Int,
    modifier: Modifier = SafeModifier,
    animationSpec: AnimationSpec<Float> = tween(400)
) {
    val pageCache = remember {
        mutableListOf<Int>()
    }
    var selectedPage by remember {
        pageCache.add(selected)
        mutableIntStateOf(selected)
    }
    var preSelectedPage by remember {
        mutableIntStateOf(selected)
    }

    var target by remember {
        mutableFloatStateOf(1f)
    }
    val animPr by animateFloatAsState(
        targetValue = target, label = "animPr", animationSpec = animationSpec
    ) {
        preSelectedPage = selectedPage
    }

    if (selected != selectedPage) {
        selectedPage = selected
        target = abs(1 - target)
        pageCache.remove(selected)
        pageCache.add(selected)
    }

    val realProgress = if (target == 0f) 1f - animPr else animPr

    val width = LocalConfiguration.current.screenWidthDp.dp
    Box(modifier) {
        if (pageCache.isEmpty()) {
            pages[selectedPage](SafeModifier)
        } else for (page in pageCache) {
            pages[page](
                if ((page != selected && page != preSelectedPage)) {
                    SafeModifier.alpha(0f)
                } else {
                    val alpha =
                        .5f + .5f * if (page == selectedPage) realProgress else 1f - realProgress
                    if (page == preSelectedPage) logError("alpha $page $alpha")
                    SafeModifier
                        .alpha(alpha)
                        .offset(
                            width * (if (page == selectedPage) 1 - realProgress else -realProgress) * selected.compareTo(
                                preSelectedPage
                            ), 0.sdp
                        )
                }
            )
        }

    }
}