package common.hoangdz.lib.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import common.hoangdz.lib.R
import common.hoangdz.lib.databinding.LayoutToolbarViewBinding
import common.hoangdz.lib.extensions.dimenFloat
import common.hoangdz.lib.extensions.dimenInt
import common.hoangdz.lib.extensions.doOnViewDrawnRepeat
import common.hoangdz.lib.extensions.gone
import common.hoangdz.lib.extensions.layoutInflater
import common.hoangdz.lib.extensions.onAvoidDoubleClick
import common.hoangdz.lib.extensions.setTint
import common.hoangdz.lib.extensions.visible
import kotlin.math.max


/**
 * Created by Hoang Dep Trai on 05/24/2022.
 */

@Keep
class ToolBar : BaseInflateCustomView<LayoutToolbarViewBinding> {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyAttrs(attrs)
    }

    private val menuItemPadding = context.dimenInt(com.intuit.sdp.R.dimen._6sdp)

    var iconSize = context.dimenInt(com.intuit.sdp.R.dimen._32sdp)
        set(value) {
            field = value
            binding.imgBack.layoutParams.apply {
                width = value
                height = value
            }
        }

    private val menuItems by lazy { mutableListOf<MenuItem>() }

    private val menuItemViews by lazy { mutableListOf<ImageView>() }

    fun setToolbarContent(view: View) {
        binding.tvTitle.gone()
        binding.lnContent.visible()
        binding.lnContent.removeAllViews()
        binding.lnContent.addView(view)
    }

    fun addMenuItem(menuItem: MenuItem) {
        menuItems.add(menuItem)
        ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(iconSize, iconSize)
            setPadding(menuItemPadding, menuItemPadding, menuItemPadding, menuItemPadding)
            onAvoidDoubleClick {
                menuItem.action?.invoke()
            }
            setTint(colorTint)
            menuItemViews.add(this)
            binding.lnAction.addView(this)
            displayMenuOrNot(menuItem)
        }
    }

    private fun ImageView.displayMenuOrNot(menuItem: MenuItem) {
        setImageResource(menuItem.icon)
        visibility = menuItem.visibility
        alpha = if (menuItem.isActive) 1f else 0.6f
        isEnabled = menuItem.isActive
    }

    fun updateItemAt(position: Int, onUpdate: (MenuItem?) -> Unit) {
        onUpdate(if (position !in menuItems.indices) null else menuItems[position])
        if (position in menuItems.indices)
            menuItemViews[position].displayMenuOrNot(menuItems[position])
    }

    var onBackListener: (() -> Unit)? = null

    var colorTint = Color.WHITE
        set(value) {
            binding.imgBack.setTint(value)
            binding.tvTitle.setTextColor(value)
            for (item in menuItemViews) {
                item.setTint(value)
            }
            field = value
        }

    var titleTextSize = context.dimenFloat(com.intuit.sdp.R.dimen._14sdp)
        set(value) {
            binding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
            field = value
        }

    var backIcon: Drawable? = null
        set(value) {
            binding.imgBack.setImageDrawable(value)
            if (value == null) hideBack() else showBack()
            field = value
        }

    fun showBack() {
        binding.imgBack.visible()
    }

    fun hideBack() {
        binding.imgBack.gone()
    }

    var title = ""
        set(value) {
            binding.tvTitle.text = value
            field = value
        }

    var titleAlignment: Int = -1
        set(value) {
            binding.tvTitle.gravity = value
            field = value
        }

    private fun updateGravityOfTitle() {
        binding.tvTitle.layoutParams.apply {
            if (this is ConstraintLayout.LayoutParams) {
                if (titleAlignment == Gravity.CENTER or Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL) {
                    val horizontal =
                        max(
                            binding.imgBack.measuredWidth,
                            binding.lnAction.measuredWidth
                        ) + menuItemPadding
                    setMargins(horizontal, menuItemPadding, horizontal, menuItemPadding)
                } else {
                    setMargins(
                        menuItemPadding + binding.imgBack.measuredWidth,
                        menuItemPadding,
                        menuItemPadding,
                        menuItemPadding
                    )
                }
                binding.tvTitle.layoutParams = this
            }
        }
    }

    @Keep
    private fun applyAttrs(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ToolBar)
        try {
            colorTint = ta.getColor(R.styleable.ToolBar_tintColor, colorTint)
            backIcon = ta.getDrawable(R.styleable.ToolBar_backIcon)
            title = ta.getString(R.styleable.ToolBar_title) ?: ""
            titleAlignment = ta.getInt(R.styleable.ToolBar_android_gravity, Gravity.START)
            titleTextSize = ta.getDimension(R.styleable.ToolBar_title_text_size, titleTextSize)
            iconSize = ta.getDimension(R.styleable.ToolBar_iconSize, iconSize * 1f).toInt()
        } finally {
            ta.recycle()
        }
    }

    init {
        doOnViewDrawnRepeat(removeCallbackBy = { false }, onDrawn = ::updateGravityOfTitle)
    }


    override fun LayoutToolbarViewBinding.onViewDrawn() {
        binding.imgBack.onAvoidDoubleClick {
            onBackListener?.invoke()
        }
    }

    data class MenuItem(
        var icon: Int,
        var visibility: Int = View.VISIBLE,
        var isActive: Boolean = true,
        var action: (() -> Unit)? = null
    )

    data class MenuItemRequester(
        val menuIndex: Int,
        val menuItem: MenuItem
    )

    override fun getViewBinding(): LayoutToolbarViewBinding {
        return LayoutToolbarViewBinding.inflate(context.layoutInflater, this, true)
    }

}