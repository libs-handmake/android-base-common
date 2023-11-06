package common.hoangdz.lib.jetpack_compose.view

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import common.hoangdz.lib.jetpack_compose.exts.SafeModifier
import common.hoangdz.lib.jetpack_compose.theme.ComposeBaseTheme

@Composable
private fun SimpleTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = SafeModifier,
    placeholder: @Composable () -> Unit = {},
    textStyle: TextStyle = TextStyle.Default,
) {
    TextField(
        placeholder = placeholder,
        textStyle = textStyle,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = ComposeBaseTheme.transparentTextFieldColors(),
        maxLines = 1,
        minLines = 1,
    )
}