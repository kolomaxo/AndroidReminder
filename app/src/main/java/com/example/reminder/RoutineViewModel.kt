package com.example.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutineViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RoutineRepository

    val allRoutines: LiveData<List<Routine>>

    init {
        val routineDAO = RoutineDatabase.getInstance(application).routineDAO()
        repository = RoutineRepository(routineDAO)
        allRoutines = repository.allRoutines
    }

    /*
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}