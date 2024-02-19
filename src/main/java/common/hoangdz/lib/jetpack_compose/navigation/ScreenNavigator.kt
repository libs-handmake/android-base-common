package common.hoangdz.lib.jetpack_compose.navigation

import android.app.Activity
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

open class ScreenNavigator(
    val route: String,
    val screenContent: (@Composable (screenConfigs: ScreenConfigs) -> Unit)? = null
)

data class ScreenConfigs(
    val route: String, val argument: Bundle? = null
) {

    val actualRouteName
        get() = route.replace("\\?.*".toRegex(), "")

    companion object {
        var navController: NavHostController? = null
    }

    fun pop(activity: Activity?, navID: String? = null) {
        if (navID.isNullOrEmpty()) navController?.popBackStack()
        else navController?.popBackStack(navID, true)
    }

    fun navigate(
        route: String,
        replacement: Boolean = false,
    ) {
        kotlin.runCatching {
            if (replacement) navigateAndReplace(route)
            else navController?.navigate(route)
        }
    }

    var onBackPressed: (() -> Boolean) = { false }
    fun navigateAndReplace(route: String) {
        navController?.navigate(route) {
            popUpTo(this@ScreenConfigs.route) {
                inclusive = true
            }
        }
    }
}


val LocalScreenConfigs = compositionLocalOf { ScreenConfigs("Nowhere_screen") }