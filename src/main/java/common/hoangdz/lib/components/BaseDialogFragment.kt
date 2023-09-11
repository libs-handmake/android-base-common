package common.hoangdz.lib.components

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import common.hoangdz.lib.R
import common.hoangdz.lib.extensions.*
import kotlin.math.roundToInt


/**
 * Created by Hoang Dep Trai on 05/31/2022.
 */

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment(), BaseAndroidComponent<VB> {

    companion object {
        val instanceManager by lazy { hashMapOf<String, BaseDialogFragment<*>>() }
    }

    override val binding by lazy { getViewBinding() }

    override fun isCancelable() = true

    override val needToSubscribeEventBus = false

    override fun getTheme() = R.style.DialogTheme

    protected open fun getDefaultDialogHeight(): Int {
        return 0
    }

    protected open fun getDefaultDialogWidthPercent(): Float {
        return .8f
    }

    override fun onStart() {
        super.onStart()
        registerEventBusBy(needToSubscribeEventBus)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(isCancelable)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.layoutParams.apply {
            val screenSize = context?.screenSize ?: return@apply
            width = (screenSize.first * getDefaultDialogWidthPercent()).roundToInt()
            height = getDefaultDialogHeight().takeIf { it > 0 } ?: WRAP_CONTENT
            binding.root.layoutParams = this
        }
        init(savedInstanceState)
    }

    override fun VB.initListener() {}

    override fun VB.initView(savedInstanceState: Bundle?) {}

    override fun VB.setupViewModel() {}

    @CallSuper
    open fun show(manager: FragmentManager) {
        if (instanceManager[this::class.java.name] != null) return
        instanceManager[this::class.java.name] = this
        val df = manager.findFragment(this) ?: this
        if (df is DialogFragment && !df.isAdded) {
            show(manager, javaClass.simpleName)
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.setReorderingAllowed(true)
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(isCancelable)
        dialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) return@setOnKeyListener !isCancelable
            return@setOnKeyListener false
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        instanceManager.remove(this::class.java.name)
        super.onDismiss(dialog)
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterEventBus()
    }
}