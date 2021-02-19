package com.example.reminder

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.time.DayOfWeek
import java.time.LocalDate

class RoutineRepository(private val routineDAO: RoutineDAO) {
    private val selectedDay = MutableLiveData<String>(DayOfWeek.of(LocalDate.now().dayOfWeek.value).toString())
    var allRoutines: LiveData<List<Routine>> =  Transformations.switchMap(selectedDay) { day ->
        routineDAO.getByWeekday(day)
    }

    @WorkerThread
    suspend fun insert(routine: Routine) {
        routineDAO.insert(routine)
    }

    fun setRoutinesForWeekday(weekDay: String?) {
        if (!weekDay.isNullOrEmpty()) {
            selectedDay.value = weekDay!!
        }
    }
}