package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.collectWhenResume() =
    collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED)