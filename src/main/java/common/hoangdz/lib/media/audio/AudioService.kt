package common.hoangdz.lib.media.audio

import kotlinx.coroutines.flow.StateFlow

interface AudioService {

    val playState: StateFlow<PlayState>
    fun toggleWithSourceAsset(source: String, audioOptions: AudioOptions = AudioOptions())

    fun toggle()

    fun pause()

    fun play()

    fun stop()

    fun release()
}