package com.example.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class RoutineViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RoutineRepository
    var selectedDay = MutableLiveData<String>(DayOfWeek.of(LocalDate.now().dayOfWeek.value).toString())
    val allRoutines: LiveData<List<Routine>>

    init {
        val routineDAO = RoutineDatabase.getInstance(application).routineDAO()
        repository = RoutineRepository(routineDAO)
        allRoutines = repository.allRoutines
    }

    /*
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(routine)
    }

    fun setRoutinesForWeekday(weekDay: String) {
        selectedDay.value = weekDay
        repository.setRoutinesForWeekday(selectedDay.value)
    }
    
    fun delete(uid: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(uid)
    }
}