package com.example.reminder

import android.telephony.mbms.StreamingServiceInfo
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CompletedRoutineDAO {
    @Query("SELECT * FROM completred_routines_table")
    fun getAll(): LiveData<List<CompletedRoutine>>

    @Query("SELECT * FROM completred_routines_table WHERE date = :current_date")
    fun getByDate(current_date: String): LiveData<List<CompletedRoutine>>

    @Insert
    suspend fun insert(completed_routine: CompletedRoutine)

    @Query("DELETE FROM completred_routines_table")
    fun deleteAll()

    @Query("DELETE FROM completred_routines_table WHERE uid = :uid")
    fun delete(uid: Int)
}