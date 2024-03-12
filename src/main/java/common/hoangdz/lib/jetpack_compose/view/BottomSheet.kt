package common.hoangdz.lib.jetpack_compose.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import common.hoangdz.lib.jetpack_compose.exts.SafeModifier
import common.hoangdz.lib.jetpack_compose.exts.collectWhenResume
import common.hoangdz.lib.jetpack_compose.exts.toComposeDP
import common.hoangdz.lib.viewmodels.BottomSheetViewModel
import kotlinx.coroutines.Job

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
    val sheetPg by sheetViewModel.sheetPG.collectWhenResume()
    var _dragAlpha by remember {
        mutableFloatStateOf(0f)
    }
    Box(
        SafeModifier
            .alpha(sheetPg)
            .fillMaxSize()
    ) {
        Box(modifier = SafeModifier
            .align(Alignment.BottomCenter)
            .offset(
                y = height.toComposeDP() * (1f - sheetPg)
            )
            .draggable(orientation = Orientation.Vertical, state = rememberDraggableState {
                _dragAlpha = (_dragAlpha + it).coerceIn(0f..height * 1f)
                val pg = 1f - _dragAlpha * 1f / height
                sheetViewModel.animateTo(pg)
            }, onDragStopped = {
                val pg = _dragAlpha * 1f / height
                if (pg < .5f) {
                    sheetViewModel.expand()
                } else {
                    sheetViewModel.hide()
                }
            })
            .then(modifier)
            .onGloballyPositioned {
                height = it.size.height
            }) {
            sheetContent()
        }
    }
}