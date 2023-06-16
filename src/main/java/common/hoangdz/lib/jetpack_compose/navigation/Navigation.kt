package common.hoangdz.lib.jetpack_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(vararg screenNavigators: ScreenNavigator) {
    if (screenNavigators.isEmpty()) return
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = screenNavigators.first().route) {
        for (nav in screenNavigators) {
            composable(route = nav.route, arguments = nav.argument) {
                nav.screenContent?.invoke(
                    ScreenConfigs(
                        navController,
                        nav.argument
                    )
                )
            }
        }
    }
}