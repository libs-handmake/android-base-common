package common.hoangdz.lib.jetpack_compose.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.hoangdz.lib.jetpack_compose.exts.SafeModifier

@Composable
fun ScreenTemplate(
    backGround: @Composable () -> Unit = {},
    foreground: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(modifier = SafeModifier.fillMaxSize()) {
        backGround()
        Box(
            modifier = SafeModifier
                .safeDrawingPadding()
                .fillMaxSize()
        ) {
            content()
        }
        foreground()
    }
}