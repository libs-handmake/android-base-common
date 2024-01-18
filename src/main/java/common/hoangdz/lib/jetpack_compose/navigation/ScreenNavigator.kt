package common.hoangdz.lib.jetpack_compose.navigation

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

    companion object{
        var navController: NavHostController? = null
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