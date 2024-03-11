package common.hoangdz.lib.jetpack_compose.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import common.hoangdz.lib.extensions.logError
import common.hoangdz.lib.jetpack_compose.exts.SafeModifier
import common.hoangdz.lib.jetpack_compose.exts.collectWhenResume
import common.hoangdz.lib.jetpack_compose.exts.toComposeDP
import common.hoangdz.lib.viewmodels.BottomSheetViewModel
import common.hoangdz.lib.viewmodels.SheetState

@Composable
fun ComposeBottomSheet(
    sheetViewModel: BottomSheetViewModel,
    modifier: Modifier = SafeModifier,
    scrollable: Boolean = true,
    sheetContent: @Composable () -> Unit
) {
    var height by remember {
        mutableIntStateOf(0)
    }
    val sheetState by sheetViewModel.sheetState.collectWhenResume()
    val animPg by animateFloatAsState(
        targetValue = if (sheetState == SheetState.HIDDEN) 0f else 1f, label = "BottomSheet"
    )
    val scrollState = rememberScrollState()
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        SafeModifier.fillMaxSize()
    ) {
        Box(modifier = SafeModifier
            .align(Alignment.BottomCenter)
            .offset(y = height.toComposeDP() * (1f - animPg))
            .clickable(interactionSource, null) {

            }
            .then(modifier)
            .onGloballyPositioned {
                height = it.size.height
                logError("height of sheet ${it.size.height}")
            }
            .alpha(animPg)
            .verticalScroll(scrollState, scrollable)) {
            sheetContent()
        }
    }
}