package com.sob3r.chattilo.userdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [UserData::class],
    version = 2,
    exportSchema = true
)
abstract class UserDataDB : RoomDatabase() {
    abstract fun userDataDao(): UserDataDao

    companion object {

        @Volatile
        private var INSTANCE: UserDataDB? = null

        fun getDatabase(context: Context): UserDataDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDataBase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDataBase(context: Context): UserDataDB{
            return Room.databaseBuilder(
                context.applicationContext,
                UserDataDB::class.java,
                "user_database"
            ).build()
        }
    }
}