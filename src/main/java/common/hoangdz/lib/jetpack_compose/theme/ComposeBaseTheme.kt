package common.hoangdz.lib.jetpack_compose.theme

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object ComposeBaseTheme {
    @Composable
    fun transparentTextFieldColors() = TextFieldDefaults.colors(
        disabledTextColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
}