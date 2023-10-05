package common.hoangdz.lib.extensions

import android.app.Activity
import android.app.Dialog
import android.app.LocaleManager
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.hardware.display.DisplayManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.LocaleList
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import common.hoangdz.lib.components.BaseActivity
import dagger.hilt.android.EntryPointAccessors
import java.util.Locale
import kotlin.math.abs


fun Context.getDisplayMetrics(): DisplayMetrics {
    return DisplayMetrics().apply {
        getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
    }
}

fun Context.getDisplayWidth() = getDisplayMetrics().widthPixels

fun Context.getDisplayHeight() = getDisplayMetrics().heightPixels

fun Context.dpToPx(dp: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics
).toInt()

fun Context.toastMsg(msg: Int) =
    Toast.makeText(this, this.getString(msg), Toast.LENGTH_SHORT).show()

fun Context.toastMsg(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

@ColorInt
fun Context.getColorR(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Context.getColorHex(@ColorRes res: Int) = colorIntToHex(getColorR(res))

fun Context.drawable(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context.tintedDrawable(@DrawableRes drawableId: Int, @ColorRes colorId: Int): Drawable? {
    val tint: Int = getColorR(colorId)
    val drawable = drawable(drawableId)
    drawable?.mutate()
    drawable?.let {
        it.mutate()
        DrawableCompat.setTint(it, tint)
    }
    return drawable
}

fun Context.string(@StringRes res: Int): String {
    return getString(res)
}

fun Context.hideSoftKeyboard(v: View) {
    val inputMethodManager: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
    v.clearFocus()
}

fun Context.showSoftKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Dialog.showSoftKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View.showSoftKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideSoftKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Dialog.hideSoftKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun AppCompatActivity.hideSoftKeyboard() {
    val inputMethodManager = getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    if (inputMethodManager.isAcceptingText) {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken, 0
        )
    }
}


fun Fragment.hideSoftKeyboard() {
    val inputMethodManager = activity?.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager?
    if (inputMethodManager?.isAcceptingText == true) {
        inputMethodManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken ?: return, 0
        )
    }
}


inline fun <reified T : Activity> Context.runActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun <reified T : Activity> Fragment.runActivity(block: Intent.() -> Unit = {}) {
    requireActivity().runActivity<T>(block)
}

fun Context.copyClipboard(text: String, label: String = "") {
    val clipboard: ClipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}


fun Context.openLink(url: String, haveAnError: (() -> Unit)? = null) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        flags = flags or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    try {
        startActivity(intent)
    } catch (e: Exception) {
        haveAnError?.invoke()
        e.printStackTrace()
    }
}

fun Context.searchGoogle(text: String) {
    val uri = Uri.parse("http://www.google.com/search?q=$text")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
        startActivity(intent)
    } catch (e: Exception) {
        openLink(text) {

        }
    }
}

fun Context.searchEbay(text: String) {
    val url = "https://www.ebay.com/sch/i.html?_nkw=$text"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.searchAmazon(text: String) {
    val url = "https://www.amazon.com/s?k=$text"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.searchWeb(text: String) {
    val intent = Intent(Intent.ACTION_WEB_SEARCH)

    intent.putExtra("query", text)
    try {
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


val Context.vibrator: Vibrator?
    get() = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

val Context.wifiManager: WifiManager?
    get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager

val Context.clipboardManager: ClipboardManager?
    get() = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager

val Context.clipboardText
    get() = clipboardManager?.primaryClip?.takeIf { it.itemCount > 0 }?.getItemAt(0)?.text ?: ""

inline fun <reified T> Context.appInject() =
    EntryPointAccessors.fromApplication(this, T::class.java)

inline fun <reified T> Activity.activityInject() =
    EntryPointAccessors.fromActivity(this, T::class.java)

fun Context.dimenInt(@DimenRes dimen: Int) = resources.getDimensionPixelSize(dimen)

fun Context.dimenFloat(@DimenRes dimen: Int) = resources.getDimension(dimen)

fun View.dimenInt(@DimenRes dimen: Int) = resources.getDimensionPixelSize(dimen)

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.linkAppStore() = "http://play.google.com/store/apps/details?id=${this.packageName}"

fun Context.goToAppStore() {
    val uri: Uri = Uri.parse("market://details?id=${this.packageName}")
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        this.startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW, Uri.parse(linkAppStore())
            )
        )
    }
}

fun Context.goToSettingNetwork() {
    try {
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    } catch (ignored: ActivityNotFoundException) {
    }
}

fun BaseActivity<*>.makeFullScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else window.addFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun BaseActivity<*>.exitFullScreenMode() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window, binding.root
        ).show(WindowInsetsCompat.Type.systemBars())
    } else window.clearFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

val Activity.statusBarHeightOld: Int
    get() {
        var result = 0
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

val Activity.statusBarHeight: Int
    get() {
        val rectangle = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        val statusBarHeight: Int = rectangle.top
        val contentViewTop: Int = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
        return abs(contentViewTop - statusBarHeight)
    }

val Activity.safeArea: Rect
    get() {
        val safeInsetRect = Rect()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return safeInsetRect
        }

        val windowInsets: WindowInsets = window.decorView.rootWindowInsets ?: return safeInsetRect

        val displayCutout = windowInsets.displayCutout
        if (displayCutout != null) {
            safeInsetRect[displayCutout.safeInsetLeft, displayCutout.safeInsetTop, displayCutout.safeInsetRight] =
                displayCutout.safeInsetBottom
        }

        return safeInsetRect
    }

val Activity.navigationBarHeight: Int
    get() {
        val resources = resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

val Context.screenSize
    get() = resources.displayMetrics.let { it.widthPixels to it.heightPixels }

fun Context.createVibratorSupport(): Vibrator {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val manager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        manager.defaultVibrator
    } else {
        @Suppress("DEPRECATION") getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
}

fun Vibrator.vibrateSupport(millis: Long) {
    if (!hasVibrator()) return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION") vibrate(millis)
    }
}

fun AppCompatActivity.createPermissionLauncher(
    onDenied: (() -> Unit)? = null, onGrant: (() -> Unit)? = null
) = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
    if (it) onGrant?.invoke()
    else onDenied?.invoke()
}

fun Fragment.createPermissionLauncher(
    onDenied: (() -> Unit)? = null, onGrant: (() -> Unit)? = null
) = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
    if (it) onGrant?.invoke()
    else onDenied?.invoke()
}

fun Context.checkPermission(permission: String) = ContextCompat.checkSelfPermission(
    this, permission
) == PackageManager.PERMISSION_GRANTED

fun ActivityResultLauncher<String>.launchPermissionIfNeeded(
    fragment: Fragment, permission: String, onPermissionRequest: ((Boolean) -> Boolean) = { true }
) {
    if (!fragment.shouldShowRequestPermissionRationale(permission)) {
        launch(permission)
        onPermissionRequest(true)
    } else {
        if (onPermissionRequest(false)) fragment.context?.openAppSetting()
    }
}

fun Context.openNotificationSetting(channelID: String? = null) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val settingsIntent: Intent =
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName).apply {
                    channelID?.let { putExtra(Settings.EXTRA_CHANNEL_ID, it) }
                }
        try {
            startActivity(settingsIntent)
        } catch (e: ActivityNotFoundException) {
            openAppSetting()
        }
    } else {
        openAppSetting()
    }
}

fun ActivityResultLauncher<String>.launchPermissionIfNeeded(
    activity: Activity, permission: String, onPermissionRequest: ((Boolean) -> Boolean) = { true }
) {
    if (!activity.shouldShowRequestPermissionRationale(permission)) {
        launch(permission)
        onPermissionRequest(true)
    } else {
        if (onPermissionRequest(false)) activity.openAppSetting()
    }
}

fun Context.openAppSetting() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}

fun Context.shareText(content: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}

fun Context.openFilePicker(
    mimeType: String,
    requestFileLauncher: ActivityResultLauncher<Intent>,
    title: String = "Choose a file"
) {
    val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
    chooseFile.type = mimeType
    requestFileLauncher.launch(Intent.createChooser(chooseFile, title))
}

fun Context.startForegroundServiceSupport(intent: Intent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else startService(intent)
}

fun Context.setLocaleSupport(code: String) {
    val locale = Locale(code)
    Locale.setDefault(locale)
    val tag = locale.toLanguageTag()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(tag)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(tag)
        )
    }
}

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}