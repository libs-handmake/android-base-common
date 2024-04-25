package common.hoangdz.lib.jetpack_compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.window.DialogProperties
import common.hoangdz.lib.jetpack_compose.exts.SafeModifier
import common.hoangdz.lib.jetpack_compose.exts.collectWhenResume
import common.hoangdz.lib.viewmodels.DialogViewModel
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeDialog(
    dialogViewModel: DialogViewModel,
    modifier: Modifier? = null,
    onDismissRequest: () -> Boolean = { true },
    properties: DialogProperties = DialogProperties(),
    DialogContent: @Composable () -> Unit
) {
    val isShow by dialogViewModel.dialogState.collectWhenResume()
    if (!isShow) return
    BasicAlertDialog(
        onDismissRequest = {
            if (onDismissRequest.invoke()) dialogViewModel.dismissDialog()
        }, modifier = modifier ?: SafeModifier
            .padding(16.sdp)
            .background(
                Color.White, RoundedCornerShape(8)
            )
            .padding(8.sdp), properties = properties
    ) {
        DialogContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeBottomSheetDialog(
    sheetViewModel: DialogViewModel,
    modifier: Modifier? = null,
    onDismissRequest: () -> Boolean = { true },
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties(),
    DialogContent: @Composable () -> Unit
) {
    val isShow by sheetViewModel.dialogState.collectWhenResume()
    if (!isShow) return
    val sheetState = rememberModalBottomSheetState(true)
    ModalBottomSheet(
        onDismissRequest = {
            if (onDismissRequest.invoke()) sheetViewModel.dismissDialog()
        },
        containerColor = Color.Transparent,
        shape = RectangleShape,
        dragHandle = {},
        properties = properties,
        sheetState = sheetState
    ) {
        Column(
            modifier = (modifier ?: SafeModifier.background(
                Color.White, RoundedCornerShape(topStartPercent = 8, topEndPercent = 8)
            )).padding(8.sdp)
        ) {
            DialogContent()
        }
    }
}