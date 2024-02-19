package common.hoangdz.lib.jetpack_compose.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import common.hoangdz.lib.extensions.getActivity
import common.hoangdz.lib.jetpack_compose.exts.SafeModifier

@Composable
fun Navigation(
    modifier: Modifier = SafeModifier,
    vararg screenNavigators: ScreenNavConfig<*>,
    backGround: @Composable (ScreenConfigs) -> Unit = {},
    foreground: @Composable (ScreenConfigs) -> Unit = {}
) {
    if (screenNavigators.isEmpty()) return
    val navController = rememberNavController()
    val owner = LocalLifecycleOwner.current
    val activity = LocalContext.current.getActivity()
    LaunchedEffect(navController) {
        ScreenConfigs.navController = navController
    }
    DisposableEffect(owner) {
        onDispose {
            ScreenConfigs.navController = null
        }
    }

    NavHost(
        navController = navController, startDestination = screenNavigators.first().routePattern
    ) {
        for (nav in screenNavigators) {
            composable(route = nav.routePattern, enterTransition = {
                nav.enterTransition()
            }, exitTransition = { nav.exitTransition() }, arguments = nav.getArgumentPattern()) {
                val config = ScreenConfigs(
                    it.destination.route ?: return@composable, it.arguments
                )
                val activity = LocalContext.current.getActivity()
                Box(modifier = modifier) {
                    Box(modifier = SafeModifier.fillMaxSize()) {
                        backGround(config)
                    }
                    BackHandler {
                        if (nav.onBackPressed(activity, config)) return@BackHandler
                        if (!navController.popBackStack()) {
                            activity?.finish()
                        }
                    }
                    CompositionLocalProvider(LocalScreenConfigs provides config) {
                        nav.BuildContent(
                            screenNavConfig = config
                        )
                    }
                    Box(modifier = SafeModifier.fillMaxSize()) {
                        foreground(config)
                    }
                }
            }
        }
    }
}