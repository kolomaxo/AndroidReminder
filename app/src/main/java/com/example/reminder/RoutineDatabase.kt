package com.example.reminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Routine::class), version = 2)
abstract class RoutineDatabase : RoomDatabase() {
    abstract fun routineDAO(): RoutineDAO

    companion object {
        @Volatile
        private var INSTANCE: RoutineDatabase? = null

        fun getInstance(context: Context): RoutineDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                                                    RoutineDatabase::class.java,
                                                    "routine_database")
                                    .fallbackToDestructiveMigration()
                                    //.addCallback(RoutineDatabaseCallback(scope))
                                    .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}
