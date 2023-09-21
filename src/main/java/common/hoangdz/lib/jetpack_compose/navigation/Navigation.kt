package common.hoangdz.lib.jetpack_compose.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    vararg screenNavigators: ScreenNavConfig<*>,
    onScreenRender: @Composable (navController: NavHostController) -> Unit = {}
) {
    if (screenNavigators.isEmpty()) return
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = screenNavigators.first().routePattern
    ) {
        for (nav in screenNavigators) {
            composable(route = nav.routePattern, enterTransition = {
                nav.enterTransition()
            }, exitTransition = { nav.exitTransition() }, arguments = nav.getArgumentPattern()) {
                onScreenRender(navController)
                Box(modifier = modifier) {
                    nav.BuildContent(
                        screenNavConfig = ScreenConfigs(
                            navController, it.arguments
                        )
                    )
                }
            }
        }
    }
}