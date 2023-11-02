package common.hoangdz.lib.lifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner

object ActivityLifecycleManager {
    private val lifeCycleCached by lazy { hashMapOf<String, LifecycleOwner>() }

    fun add(activity: AppCompatActivity) {
        lifeCycleCached[activity::class.java.name] = activity
    }

    fun remove(activity: AppCompatActivity) {
        lifeCycleCached.remove(activity::class.java.name)
    }

    operator fun get(activity: AppCompatActivity) = lifeCycleCached[activity::class.java.name]
}