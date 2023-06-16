package common.hoangdz.lib.jetpack_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController

open class ScreenNavigator(
    val route: String,
    val argument: List<NamedNavArgument> = mutableListOf(),
    val screenContent: (@Composable (screenConfigs: ScreenConfigs) -> Unit)? = null
)

data class ScreenConfigs(
    val navController: NavHostController,
    val argument: List<NamedNavArgument> = mutableListOf()
)