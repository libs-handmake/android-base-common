package common.hoangdz.lib.media.video.torch

import android.content.Context
import android.hardware.camera2.CameraManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TorchHelper @Inject constructor(@ApplicationContext private val context: Context) {
    private val cameraManager by lazy { context.getSystemService(Context.CAMERA_SERVICE) as CameraManager }

    private val cameraId by lazy {
        kotlin.runCatching { cameraManager.cameraIdList.firstOrNull() }.getOrNull()
    }

    fun startTorch() = kotlin.runCatching {
        cameraManager.setTorchMode(cameraId ?: return@runCatching, true)
    }

    fun stopTorch() = kotlin.runCatching {
        cameraManager.setTorchMode(cameraId ?: return@runCatching, false)
    }
}