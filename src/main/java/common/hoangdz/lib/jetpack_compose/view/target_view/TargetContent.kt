package common.hoangdz.lib.jetpack_compose.view.target_view

import androidx.compose.runtime.Composable

class TargetContent(val shape: TargetShape, val content: @Composable TargetScope.() -> Unit = {})