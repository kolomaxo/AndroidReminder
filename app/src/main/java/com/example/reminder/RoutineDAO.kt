package com.example.reminder

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoutineDAO {
    @Query("SELECT * FROM routines_table")
    fun getAll(): LiveData<List<Routine>>

    @Insert
    suspend fun insert(routine: Routine)

    @Query("DELETE FROM routines_table")
    fun deleteAll()
}
