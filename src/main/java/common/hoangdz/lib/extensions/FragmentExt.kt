package common.hoangdz.lib.extensions

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle


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
    beginTransaction().replace(container.id, fragment, fragment.javaClass.name).apply {
        if (isAddToBackStack)
            addToBackStack(fragment.javaClass.name)
    }.commit()

fun Fragment.removeSelf() {
    parentFragmentManager.popBackStack(javaClass.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    parentFragmentManager.remove(parentFragmentManager.findFragment(this) ?: return)
}

fun FragmentManager.remove(fragment: Fragment) = beginTransaction().remove(fragment).commitNow()

fun FragmentManager.remove(tag: String) {
    beginTransaction().remove(findFragmentByTag(tag) ?: return).commitNow()
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