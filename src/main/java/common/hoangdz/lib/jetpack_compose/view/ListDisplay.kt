package common.hoangdz.lib.jetpack_compose.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import common.hoangdz.lib.R
import common.hoangdz.lib.extensions.logError
import common.hoangdz.lib.jetpack_compose.exts.collectWhenResume
import common.hoangdz.lib.viewmodels.DataResult
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> ListDisplay(
    dataState: StateFlow<DataResult<List<T>>>,
    modifier: Modifier = Modifier,
    onPlaceHolder: @Composable (data: DataResult<List<T>>) -> Unit = {
        Box(Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.no_data),
                style = TextStyle(color = Color.Black, fontSize = 14.ssp)
            )
        }
    },
    onLoading: @Composable (data: DataResult<List<T>>) -> Unit = {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center), color = Color.DarkGray
            )
        }
    },
    onData: @Composable (List<T>) -> Unit = {},
) {
    val data by dataState.collectWhenResume()
    logError(data.state)
    when (data.state) {
        DataResult.DataState.LOADING, DataResult.DataState.IDLE -> onLoading(data)
        DataResult.DataState.LOADED -> {
            (data.value).takeIf { !it.isNullOrEmpty() }?.let { onData(it) } ?: onPlaceHolder(data)
        }

        DataResult.DataState.ERROR -> onPlaceHolder(data)
    }
}