package common.hoangdz.lib.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Created by Hoang Dep Trai on 07/01/2022.
 */

suspend fun <T> runMain(run: suspend () -> T) = withContext(Dispatchers.Main) { run() }

suspend fun <T> runIO(run: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO) { run() }

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) =
    launch(Dispatchers.IO, block = block)

fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit) =
    launch(Dispatchers.Main, block = block)


fun LifecycleOwner.launchWhen(state: Lifecycle.State, block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            block()
            this@launch.cancel()
        }
    }
}