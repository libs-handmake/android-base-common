package common.hoangdz.lib.extensions

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat

enum class StatusBarColorMode {
    LIGHT, DARK
}


fun Activity.configureWindowForTransparency() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun Activity.setStatusBarTextColorMode(mode: StatusBarColorMode) {
    val wC = WindowCompat.getInsetsController(window, window.decorView)
    wC.isAppearanceLightStatusBars = mode == StatusBarColorMode.LIGHT
    wC.isAppearanceLightNavigationBars = mode == StatusBarColorMode.LIGHT
}

fun Activity.configureStatusBarForFullscreenExperience() {/*    val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = 0x40000000
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN*/

    /*ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
        val insets = windowInsets.getInsets(
            WindowInsetsCompat.Type.systemGestures()
        )
        logError(insets)
        view.updatePadding(
            insets.left, insets.top, insets.right, insets.bottom
        )
        WindowInsetsCompat.CONSUMED
    }*/
}

fun hideNavigationButton(window: Window) {
    /*
    * note:    add <item name="android:windowTranslucentStatus">true</item> to themes style
    */
    try {
        val decorView = window.decorView
        val uiOptions =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
