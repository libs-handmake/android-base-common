package common.hoangdz.lib.media.audio

class AudioOptions(
    val isLooping: Boolean = false,
    val vibratorOptions: VibrateOptions = VibrateOptions(),
    val flashLightOptions: FlashLightOptions = FlashLightOptions(),
    val adjustVolume: Pair<Boolean, Float> = false to 0f
) {

    /**
     * @param pattern: Pattern mean vibrate, sleep, vibrate, sleep,...
     * */
    class VibrateOptions(
        val pattern: Array<Long> = arrayOf(), val enableBy: () -> Boolean = { false }
    )

    class FlashLightOptions(
        val pattern: Array<Long> = arrayOf(), val enableBy: () -> Boolean = { false }
    )
}