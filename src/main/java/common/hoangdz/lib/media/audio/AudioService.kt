package common.hoangdz.lib.media.audio

import kotlinx.coroutines.flow.StateFlow

interface AudioService {

    val playState: StateFlow<PlayState>

    fun toggleWithSourceAsset(source: String, audioOptions: AudioOptions = AudioOptions())

    fun updateAudioOptions(audioOptions: AudioOptions)

    fun updateVolume(volume: Pair<Boolean, Float>? = null)

    fun toggle()

    fun pause()

    fun play()

    fun stop()

    fun release()

    fun startTorch(flashOptions: AudioOptions.FlashLightOptions? = null)
}