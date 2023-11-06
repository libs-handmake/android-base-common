package common.hoangdz.lib.media.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TextToSpeechHelper constructor(private val context: Context) : TextToSpeech.OnInitListener {

    private var initialized = false

    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context, this, "com.google.android.tts")
    }

    override fun onInit(status: Int) {
        initialized = status == TextToSpeech.SUCCESS
    }

    var locale = Locale.ENGLISH

    fun speakText(content: String) {
        kotlin.runCatching {
            tts?.stop()
            tts?.language = locale
            tts?.speak(content, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun release() {
        kotlin.runCatching {
            tts?.stop()
            tts?.shutdown()
        }
    }


}