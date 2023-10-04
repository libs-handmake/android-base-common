package common.hoangdz.lib.jetpack_compose.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import ir.kaaveh.sdpcompose.sdp

@Composable
fun StarView(
    modifier: Modifier = Modifier,
    @DrawableRes starDrawable: Int,
    @DrawableRes unStarDrawable: Int,
    totalStar: Int = 5,
    starValue: Int = totalStar,
    onStarChange: (Int) -> Unit
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalStar) Image(modifier = Modifier
            .width(32.sdp)
            .clip(CircleShape)
            .clickable {
                onStarChange(i + 1)
            }
            .padding(4.sdp),
            painter = painterResource(id = if (i + 1 <= starValue) starDrawable else unStarDrawable),
            contentDescription = "Star ${i + 1}")
    }
}