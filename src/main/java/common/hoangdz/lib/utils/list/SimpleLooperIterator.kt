package common.hoangdz.lib.utils.list

class SimpleLooperIterator<T>(val data: List<T>) : LooperIterator<T> {
    private var index = 0
    override fun next(): T? {
        return synchronized(data) {
            val r = data.getOrNull(index)
            index++
            if (index > data.lastIndex) index = 0
            return@synchronized r
        }
    }

    override fun previous(): T? {
        return synchronized(data) {
            val r = data.getOrNull(index)
            index--
            if (index < 0) index = data.lastIndex
            return@synchronized r
        }
    }

}