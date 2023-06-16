package common.hoangdz.lib.jetpack_compose.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import common.hoangdz.lib.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    backIcon: MenuItem? = null,
    title: String = "",
    onBuildTitleStyle: @Composable (TextStyle) -> TextStyle = { it },
    menuItems: MutableList<MenuItem> = mutableListOf()
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        backIcon?.RenderMenu()
        Box(modifier = Modifier.weight(1f)) {
            title.takeIf { it.isNotEmpty() }?.let { text ->
                val textStyle = onBuildTitleStyle(TextStyle(fontSize = 16.ssp, color = Color.Black))
                Text(
                    text = text,
                    style = textStyle,
                    modifier = Modifier.padding(8.sdp)
                )

            }
        }
        menuItems.map { it.RenderMenu() }
    }
}

data class MenuItem(@DrawableRes val icon: Int, val clickListener: (() -> Unit)? = null) {

    @Composable
    fun RenderMenu() {
        Box(modifier = Modifier
            .padding(6.sdp)
            .clip(RoundedCornerShape(6.sdp))
            .clickable {
                clickListener?.invoke()
            }) {
            Image(
                painter = painterResource(id = icon),
                colorFilter = ColorFilter.tint(Color.Black),
                contentDescription = "Back icon",
                modifier = Modifier
                    .width(28.sdp)
                    .padding(6.sdp)
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Preview() {
    Toolbar(
        backIcon = MenuItem(R.drawable.ic_back_arrow),
        title = "Toolbar example title",
        menuItems = mutableListOf(MenuItem(androidx.core.R.drawable.ic_call_decline))
    )
}