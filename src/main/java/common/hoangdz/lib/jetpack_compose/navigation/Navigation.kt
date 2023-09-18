package common.hoangdz.lib.jetpack_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(vararg screenNavigators: ScreenNavConfig<*>) {
    if (screenNavigators.isEmpty()) return
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = screenNavigators.first().routePattern
    ) {
        for (nav in screenNavigators) {
            composable(route = nav.routePattern, enterTransition = {
                nav.enterTransition()
            }, exitTransition = { nav.exitTransition() }, arguments = nav.getArgumentPattern()) {
                nav.BuildContent(
                    screenNavConfig = ScreenConfigs(
                        navController, it.arguments
                    )
                )
            }
        }
    }
}