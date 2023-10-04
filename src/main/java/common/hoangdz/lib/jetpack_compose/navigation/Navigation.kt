package common.hoangdz.lib.jetpack_compose.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import common.hoangdz.lib.extensions.getActivity

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    vararg screenNavigators: ScreenNavConfig<*>,
    backGround: @Composable () -> Unit = {},
    foreGround: @Composable () -> Unit = {},
    onScreenRender: @Composable (navController: NavHostController) -> Unit = {}
) {
    if (screenNavigators.isEmpty()) return
    val navController = rememberNavController()
    val activity = LocalContext.current.getActivity()
    NavHost(
        navController = navController, startDestination = screenNavigators.first().routePattern
    ) {
        for (nav in screenNavigators) {
            composable(route = nav.routePattern, enterTransition = {
                nav.enterTransition()
            }, exitTransition = { nav.exitTransition() }, arguments = nav.getArgumentPattern()) {
                onScreenRender(navController)
                val config = ScreenConfigs(
                    navController, it.destination.route ?: return@composable, it.arguments
                )
                val activity = LocalContext.current.getActivity()
                Box(modifier = modifier) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        backGround()
                    }
                    BackHandler {
                        if (nav.onBackPressed(activity, config)) return@BackHandler
                        if (!navController.popBackStack()) {
                            activity?.finish()
                        }
                    }
                    nav.BuildContent(
                        screenNavConfig = config
                    )
                    Box(modifier = Modifier.fillMaxSize()) {
                        foreGround()
                    }
                }
            }
        }
    }
}