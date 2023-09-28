package common.hoangdz.lib.jetpack_compose.exts

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.collectWhenResume() =
    collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED)

@Composable
fun <T> Flow<T>.collectWhenResume(
    initialValue: T,
) = collectAsStateWithLifecycle(initialValue, minActiveState = Lifecycle.State.RESUMED)

fun <T> MutableStateFlow<T>.compareAndSet(update: T) {
    if (value == update) return
    value = update
}