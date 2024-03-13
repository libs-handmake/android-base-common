package common.hoangdz.lib.datasouce

class ItemUpdate<out T>(val data: T, val state: ItemState) {

    enum class ItemState {
        ADD, UPDATE, REMOVE
    }
}