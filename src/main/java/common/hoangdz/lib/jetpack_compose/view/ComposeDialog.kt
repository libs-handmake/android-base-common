package common.hoangdz.lib.jetpack_compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import common.hoangdz.lib.jetpack_compose.exts.collectWhenResume
import common.hoangdz.lib.viewmodels.DialogViewModel
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeDialog(
    dialogViewModel: DialogViewModel,
    modifier: Modifier = Modifier
        .padding(16.sdp)
        .background(Color.White, RoundedCornerShape(8))
        .padding(8.sdp),
    onDismissRequest: () -> Boolean = { true },
    properties: DialogProperties = DialogProperties(),
    DialogContent: @Composable () -> Unit
) {
    val isShow by dialogViewModel.dialogState.collectWhenResume()
    if (!isShow) return
    AlertDialog(modifier = modifier, properties = properties, onDismissRequest = {
        if (onDismissRequest.invoke()) dialogViewModel.dismissDialog()
    }) {
        DialogContent()
    }
}