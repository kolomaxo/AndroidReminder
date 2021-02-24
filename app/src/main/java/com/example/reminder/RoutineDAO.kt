package com.example.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoutineDAO {
    @Query("SELECT * FROM routines_table")
    fun getAll(): LiveData<List<Routine>>

    @Query("SELECT * FROM routines_table WHERE weekDay = :weekDay")
    fun getByWeekday(weekDay: String): LiveData<List<Routine>>

    @Insert
    suspend fun insert(routine: Routine)

    @Query("DELETE FROM routines_table")
    fun deleteAll()

    @Query("DELETE FROM routines_table WHERE uid = :uid")
    fun delete(uid: Int)
}
