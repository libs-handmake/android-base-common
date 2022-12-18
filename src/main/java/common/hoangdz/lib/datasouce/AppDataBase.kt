package common.hoangdz.lib.datasouce

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Hoangkute123xyz on 18/12/2022 at 2:00 CH.
 */

abstract class AppDataBase : RoomDatabase() {

    companion object {
        inline fun <reified T : AppDataBase> newInstance(
            context: Context,
            appDataBaseName: String
        ) =
            Room.databaseBuilder(context, T::class.java, appDataBaseName).build()
    }

}