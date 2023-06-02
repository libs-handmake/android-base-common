package common.hoangdz.lib.extensions

import android.os.Build
import android.os.Bundle

inline fun <reified T> Bundle.getParcelableSupport(key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key)
    }