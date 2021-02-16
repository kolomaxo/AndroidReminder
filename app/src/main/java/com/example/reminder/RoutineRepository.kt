package com.example.reminder

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RoutineRepository(private val routineDAO: RoutineDAO) {
    val allRoutines: LiveData<List<Routine>> = routineDAO.getAll()

    @WorkerThread
    suspend fun insert(routine: Routine) {
        routineDAO.insert(routine)
    }
}