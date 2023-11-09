package common.hoangdz.lib.jetpack_compose.view.target_view

import androidx.compose.runtime.Composable

class TargetContent(
    val id: String,
    val shape: TargetShape,
    val onTargetClicked: (() -> Unit)? = null,
    val onTargetCancel: (() -> Unit)? = null,
    val onTargetSkip: (() -> Unit)? = null,
    val content: @Composable TargetScope.() -> Unit = {}
)