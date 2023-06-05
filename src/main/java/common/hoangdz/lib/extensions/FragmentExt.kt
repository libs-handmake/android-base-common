package common.hoangdz.lib.extensions

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import common.hoangdz.lib.R


/**
 * Created by Hoang Dep Trai on 07/02/2022.
 */

fun FragmentManager.replace(container: ViewGroup, fragment: Fragment) =
    beginTransaction().replace(container.id, fragment, fragment.javaClass.name).commit()

fun FragmentManager.add(
    container: ViewGroup,
    fragment: Fragment,
    isAddToBackStack: Boolean = true
) =
    beginTransaction().add(container.id, fragment, fragment.javaClass.name).apply {
        setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        if (isAddToBackStack)
            addToBackStack(fragment.javaClass.name)
    }.commit()

fun Fragment.removeSelf() {
    parentFragmentManager.remove(this)
}

fun FragmentManager.remove(fragment: Fragment) = beginTransaction().remove(fragment).commit()

fun FragmentManager.remove(tag: String) {
    beginTransaction().remove(findFragmentByTag(tag) ?: return).commit()
}

fun FragmentManager.findFragment(fragment: Fragment) =
    findFragmentByTag(fragment.javaClass.name)

inline fun <reified T : Fragment> FragmentManager.findFragment(): T? =
    findFragmentByTag(T::class.java.name) as T?

fun FragmentManager.showFragment(fragment: Fragment) = beginTransaction().show(fragment).commit()

fun FragmentManager.hideFragment(fragment: Fragment) = beginTransaction().hide(fragment).commit()

fun FragmentManager.forcePause(fragment: Fragment) =
    beginTransaction().hide(fragment).setMaxLifecycle(fragment, Lifecycle.State.STARTED)

fun FragmentManager.forceResume(fragment: Fragment) =
    beginTransaction().setMaxLifecycle(fragment, Lifecycle.State.RESUMED).show(fragment)