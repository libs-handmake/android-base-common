package common.hoangdz.lib.jetpack_compose.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import common.hoangdz.lib.R
import common.hoangdz.lib.jetpack_compose.exts.toComposeDP
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlin.math.max

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    backIcon: MenuItem? = null,
    title: String = "",
    onBuildTitleStyle: @Composable (TextStyle) -> TextStyle = { it },
    menuItems: MutableList<MenuItem> = mutableListOf()
) {
    var sizeLeft by remember {
        mutableIntStateOf(0)
    }
    var sizeRight by remember {
        mutableIntStateOf(0)
    }
    val textStyle = onBuildTitleStyle(
        TextStyle(
            fontSize = 14.ssp, color = Color.Black, fontWeight = FontWeight.Bold
        )
    )
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        backIcon?.RenderMenu(Modifier.onGloballyPositioned {
            sizeLeft = it.size.width
        })
        Box(modifier = Modifier.weight(1f).run {
            if (textStyle.textAlign == TextAlign.Center) padding(
                start = max(sizeRight - sizeLeft, 0).toComposeDP(),
                end = max(sizeLeft - sizeRight, 0).toComposeDP()
            )
            else this
        }) {
            title.takeIf { it.isNotEmpty() }?.let { text ->
                Text(
                    text = text,
                    style = textStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.sdp)
                )

            }
        }
        Row(Modifier.onGloballyPositioned {
            sizeRight = it.size.width
        }) {
            menuItems.map { it.RenderMenu() }
        }
    }
}

data class MenuItem(
    @DrawableRes val icon: Int? = null,
    val itemView: (@Composable () -> Unit)? = null,
    val clickListener: (() -> Unit)? = null
) {

    @Composable
    fun RenderMenu(modifier: Modifier = Modifier) {
        Box(modifier) {
            Box(modifier = Modifier
                .padding(4.sdp)
                .clip(RoundedCornerShape(6.sdp))
                .clickable {
                    clickListener?.invoke()
                }
                .padding(4.sdp)) {
                icon?.let {
                    Image(
                        painter = painterResource(id = icon),
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentDescription = "Back icon",
                        modifier = Modifier
                            .width(28.sdp)
                            .padding(6.sdp)
                    )
                } ?: itemView?.invoke()

            }
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