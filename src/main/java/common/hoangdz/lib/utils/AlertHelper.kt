package common.hoangdz.lib.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AlertHelper {
    fun showMaterialDialog(context: Context, onBuilder: (MaterialAlertDialogBuilder.() -> Unit)) =
        MaterialAlertDialogBuilder(context).apply(onBuilder).create().also { it.show() }

}