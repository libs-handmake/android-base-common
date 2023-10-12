package common.hoangdz.lib.jetpack_compose.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScreenTemplate(
    backGround: @Composable () -> Unit = {},
    foreground: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        backGround()
        Box(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxWidth()
        ) {
            content()
        }
        foreground()
    }
}