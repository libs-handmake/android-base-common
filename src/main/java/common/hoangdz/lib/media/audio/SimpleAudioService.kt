package common.hoangdz.lib.media.audio

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import common.hoangdz.lib.Constant
import common.hoangdz.lib.extensions.createVibratorSupport
import common.hoangdz.lib.extensions.launchIO
import common.hoangdz.lib.extensions.vibrateSupport
import common.hoangdz.lib.media.video.torch.TorchHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SimpleAudioService(
    private val context: Context, private val torchHelper: TorchHelper
) : AudioService {

    private val scope by lazy { CoroutineScope(Dispatchers.IO) }

    private val _playState by lazy { MutableStateFlow(PlayState.IDLE) }
    override val playState by lazy { _playState.asStateFlow() }

    private val vibrationService by lazy { context.createVibratorSupport() }

    private val audioManager by lazy { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    private var mediaPlayer: MediaPlayer? = null

    private var source: String? = null

    private var currentOptions: AudioOptions? = null

    private var mediaPreparedJob: Job? = null

    private var vibrateJob: Job? = null

    private var flashLightJob: Job? = null

    override fun toggleWithSourceAsset(source: String, audioOptions: AudioOptions) {
        val rSource = source.replace(Constant.ASSET_FILE_PATH, "")
        this.currentOptions = audioOptions
        if (this.source == rSource) {
            toggle()
            return
        }
        this.source = rSource
        sync {
            releaseMediaPlayer()
            _playState.value = PlayState.IDLE
            val afd =
                kotlin.runCatching { context.assets.openFd(rSource) }.getOrNull() ?: kotlin.run {
                    this.source = null
                    return@sync
                }
            kotlin.runCatching {
                mediaPlayer = MediaPlayer().apply {
                    isLooping = audioOptions.isLooping
                    setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    _playState.value = PlayState.BUFFERING
                    prepare()
                    toggle()
                }
            }.getOrNull() ?: kotlin.run {
                this.source = null
            }
            kotlin.runCatching {
                afd.close()
            }
        }
    }

    override fun updateAudioOptions(audioOptions: AudioOptions) {
        sync {
            this.currentOptions = audioOptions
            updateVolume()
        }
    }

    private fun startTorch() {
        stopTorch()
        val flashLightOptions = currentOptions?.flashLightOptions ?: return
        if (!flashLightOptions.enableBy()) return
        flashLightJob = scope.launchIO {
            for ((index, d) in flashLightOptions.pattern.withIndex()) {
                if (d <= 0) continue
                if (index % 2 == 0) {
                    torchHelper.startTorch()
                } else {
                    torchHelper.stopTorch()
                }
                delay(d)
            }
            startTorch()
        }
    }

    private fun stopTorch() {
        flashLightJob?.cancel()
        flashLightJob = null
        torchHelper.stopTorch()
    }

    private fun vibrate() {
        stopVibration()
        val vibrateOptions = currentOptions?.vibratorOptions ?: return
        if (!vibrateOptions.enableBy()) return
        vibrateJob = scope.launchIO {
            for ((index, d) in vibrateOptions.pattern.withIndex()) {
                if (d <= 0) continue
                if (index % 2 == 0) {
                    vibrationService.vibrateSupport(d)
                }
                delay(d)
            }
            vibrate()
        }
    }

    private fun stopVibration() {
        vibrateJob?.cancel()
        vibrateJob = null
        vibrationService.cancel()
    }

    override fun toggle() {
        sync {
            if (playState.value == PlayState.PLAYING) pause()
            else play()
        }
    }

    override fun pause() {
        sync {
            mediaPlayer?.pause()
            _playState.value = PlayState.PAUSED
            stopVibration()
            stopTorch()
        }
    }

    override fun play() {
        sync {
            mediaPlayer?.start()
            _playState.value = PlayState.PLAYING
            vibrate()
            startTorch()
            updateVolume()
        }
    }

    override fun updateVolume(volume: Pair<Boolean, Float>?) {
        currentOptions?.let { options ->
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val volume = volume ?: options.adjustVolume
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                (maxVolume * ((volume.second).takeIf { !volume.first } ?: 0f)).toInt(),
                0)
        }
    }

    override fun stop() {
        sync {
            mediaPlayer?.stop()
            _playState.value = PlayState.PAUSED
            stopVibration()
            stopTorch()
        }
    }

    override fun release() {
        sync {
            source = null
            releaseMediaPlayer()
        }
    }

    private fun releaseMediaPlayer() {
        stopVibration()
        stopTorch()
        mediaPreparedJob?.cancel()
        mediaPreparedJob = null
        mediaPlayer?.release()
        mediaPlayer = null
        _playState.value = PlayState.RELEASED
    }

    private fun sync(block: () -> Unit) {
        scope.launchIO {
            synchronized(playState) {
                runCatching(block)
            }
        }
    }
}