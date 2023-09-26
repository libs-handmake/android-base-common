package common.hoangdz.lib.utils.language

import android.content.Context
import java.util.Locale

object FlagHelper {
    fun getFlagDrawable(context: Context, langCode: String): Int {
        return context.resources.getIdentifier(
            langCode.lowercase(
                Locale.ENGLISH
            ), "drawable", context.packageName
        )
    }
}