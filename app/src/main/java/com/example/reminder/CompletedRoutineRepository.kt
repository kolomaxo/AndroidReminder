package com.example.reminder

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.time.DayOfWeek
import java.time.LocalDate

class CompletedRoutineRepository(private val completedRoutineDAO: CompletedRoutineDAO) {
    private val selectedDate = MutableLiveData<String>(DayOfWeek.of(LocalDate.now().dayOfWeek.value).toString())
    var allCompletedRoutines: LiveData<List<CompletedRoutine>> =  Transformations.switchMap(selectedDate) { date ->
        completedRoutineDAO.getByDate(date)
    }

    @WorkerThread
    suspend fun insert(completedRoutine: CompletedRoutine) {
        completedRoutineDAO.insert(completedRoutine)
    }
    /*
    fun setRoutinesForWeekday(weekDay: String?) {
        if (!weekDay.isNullOrEmpty()) {
            selectedDay.value = weekDay!!
        }
    }
    */
    @WorkerThread
    suspend fun delete(uid: Int) {
        completedRoutineDAO.delete(uid)
    }
}