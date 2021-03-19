package com.example.reminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CompletedRoutine::class), version = 1)
abstract class CompletedRoutineDatabase : RoomDatabase() {
    abstract fun completedRoutineDAO(): CompletedRoutineDAO

    companion object {
        @Volatile
        private var INSTANCE: CompletedRoutineDatabase? = null

        fun getInstance(context: Context): CompletedRoutineDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            CompletedRoutineDatabase::class.java,
                            "completed_routine_database")
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
