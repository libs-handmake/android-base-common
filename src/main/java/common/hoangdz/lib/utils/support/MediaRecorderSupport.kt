package common.hoangdz.lib.utils.support

import android.content.Context
import android.media.MediaRecorder
import android.os.Build

class MediaRecorderSupport {

    private constructor()

    companion object {
        fun newInstance(context: Context) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION") MediaRecorder()
        }
    }
}