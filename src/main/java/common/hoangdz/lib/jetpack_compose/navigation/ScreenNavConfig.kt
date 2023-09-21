package common.hoangdz.lib.jetpack_compose.navigation

import android.os.Bundle
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import common.hoangdz.lib.extensions.urlEncoded
import java.util.regex.Pattern

abstract class ScreenNavConfig<T> {

    abstract val routePattern: String

    fun getArgumentPattern(): List<NamedNavArgument> {
        val pattern = "\\?(.+)".toRegex()
        val res = mutableListOf<NamedNavArgument>()
        val argument = pattern.find(routePattern)?.groupValues?.getOrNull(1) ?: return res
        val arPat = Pattern.compile("(.*?)=(((.*?)&)|(.*))")
        val matcher = arPat.matcher(argument)
        while (matcher.find()) {
            res.add(navArgument(
                matcher.group(1) ?: continue
            ) {
                nullable = true
            })
        }
        return res
    }

    open fun navigationInfo(data: T? = null): String = routePattern
    fun makeRealRoute(bundle: Bundle?): String {
        var r = routePattern.replace("\\?(.*?)$".toRegex(), "")
        bundle?.keySet()?.forEach {
            if (bundle.getString(it).isNullOrEmpty()) return@forEach
            r += if (r.contains("?")) "&"
            else "?"
            r += "${it}=${bundle.getString(it)?.urlEncoded()}"
        }
        return r
    }

    @Composable
    abstract fun BuildContent(screenNavConfig: ScreenConfigs)

    open fun enterTransition(): EnterTransition {
        val duration = 200
        return slideInHorizontally(TweenSpec(duration)) {
            it
        } + fadeIn(TweenSpec(duration),.3f)
    }

    open fun exitTransition(): ExitTransition {
        val duration = 200
        return slideOutHorizontally(TweenSpec(durationMillis = duration)) {
            it
        } + fadeOut(TweenSpec(duration),.3f)
    }
}