package common.hoangdz.lib.datasouce

import android.content.Context
import androidx.room.Room
import java.lang.reflect.ParameterizedType

/**
 * Created by Hoangkute123xyz on 18/12/2022 at 7:10 CH.
 */
abstract class DatabaseHandle<T : AppDataBase>(private val context: Context) {

    protected abstract val databaseName: String

    @Suppress("UNCHECKED_CAST")
    val database by lazy {
        val clazz =
            (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        Room.databaseBuilder(context, clazz, databaseName).build()
    }

}