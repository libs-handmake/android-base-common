package common.hoangdz.lib.jetpack_compose.exts

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import common.hoangdz.lib.R

fun NavHostController.navigateAndReplace(
    route: String,
    replacement: Boolean = true,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    if (replacement) popBackStack()
    navigate(route, navOptions, navigatorExtras)
}

fun NavOptions.Builder.noAnimation() =
    setEnterAnim(R.anim.empty).setExitAnim(R.anim.empty).setPopEnterAnim(R.anim.empty)
        .setPopExitAnim(R.anim.empty)