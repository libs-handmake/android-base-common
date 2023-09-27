package common.hoangdz.lib.utils.list

interface LooperIterator<out T> {
    fun next(): T?

    fun previous(): T?
}