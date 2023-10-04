package common.hoangdz.lib.jetpack_compose.view.target_view

import common.hoangdz.lib.jetpack_compose.exts.compareAndSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs

internal class InstanceTargetScope(
    vararg val content: TargetContent,
    val onTargetClicked: ((TargetContent) -> Unit)? = null,
    val onTargetClosed: (() -> Unit)? = null,
    val onTargetCancel: (() -> Unit)? = null,
) : TargetScope {

    private var currentIndex = 0

    private val _showingContent by lazy { MutableStateFlow(true) }
    val showingContent by lazy { _showingContent.asStateFlow() }

    private val _targetAnim by lazy { MutableStateFlow(0f) }
    val targetAnim by lazy { _targetAnim.asStateFlow() }


    fun reverseAnim() {
        _targetAnim.compareAndSet(abs(1f - _targetAnim.value))
    }

    val currentContent
        get() = content[currentIndex]

    override fun moveToNextTarget() {
        onTargetClicked?.invoke(currentContent)
        if (currentIndex < content.lastIndex) currentIndex++
        else skipAllTarget()
    }

    override fun skipAllTarget() {
        _showingContent.value = false
        onTargetClosed?.invoke()
    }

    override fun cancelTarget() {
        _showingContent.value = false
        onTargetCancel?.invoke()
    }
}