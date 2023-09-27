package common.hoangdz.lib.jetpack_compose.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

open class ScreenNavigator(
    val route: String,
    val screenContent: (@Composable (screenConfigs: ScreenConfigs) -> Unit)? = null
)

data class ScreenConfigs(
    val navController: NavHostController, val route: String, val argument: Bundle? = null
) {
    fun navigateAndReplace(route: String) {
        navController.navigate(route) {
            popUpTo(this@ScreenConfigs.route) {
                inclusive = true
            }
        }
    }
}