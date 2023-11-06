package common.hoangdz.lib.extensions

import android.graphics.Bitmap
import android.graphics.Matrix

fun Bitmap.resizeWith(newWidth: Int, newHeight: Int): Bitmap {
    val scaleWidth = newWidth * 1f / width
    val scaleHeight = newHeight * 1f / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    val resizedBitmap = Bitmap.createBitmap(
        this, 0, 0, width, height, matrix, false
    )
    recycle()
    return resizedBitmap
}