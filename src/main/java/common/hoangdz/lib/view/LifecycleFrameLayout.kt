package common.hoangdz.lib.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class LifecycleFrameLayout : FrameLayout, LifecycleOwner {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        } else {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        }
    }

    override fun onDetachedFromWindow() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        registry.currentState = Lifecycle.State.DESTROYED
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    private val registry by lazy { LifecycleRegistry(this) }

    override fun getLifecycle(): Lifecycle {
        return registry
    }


}