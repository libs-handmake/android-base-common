package common.hoangdz.lib.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_ANY
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

abstract class BaseLifecycleEvent : LifecycleEventObserver {
    var currentState = ON_ANY
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        currentState = event
        when (event) {
            ON_CREATE -> onCreate()
            ON_DESTROY -> onDestroy()
            ON_STOP -> onStop()
            ON_RESUME -> onResume()
            ON_PAUSE -> onPause()
            ON_START -> onStart()
            ON_ANY -> onAny()
        }
    }

    protected open fun onAny() {

    }

    protected open fun onStart() {
    }

    protected open fun onPause() {
    }

    protected open fun onResume() {
    }

    protected fun onStop() {}

    protected open fun onDestroy() {}

    protected open fun onCreate() {}
}