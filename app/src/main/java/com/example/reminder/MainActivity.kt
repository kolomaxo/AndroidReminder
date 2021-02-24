package com.example.reminder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.DayOfWeek
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val createNewRoutineActivityRequestCode = 1
    private lateinit var routineViewModel: RoutineViewModel
    private var currentDayNumber = LocalDate.now().dayOfWeek.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setHeaderText()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = RoutineListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

        routineViewModel.allRoutines.observe(this, Observer { routines ->
            routines?.let { adapter.setRoutines(it) }
        })
    }

    /** Called when the user taps (+)
     * Opens view to create new routine*/
    fun addNewRoutine(view: View) {
        val intent = Intent(this, CreateNewRoutineActivity::class.java)
        startActivityForResult(intent, createNewRoutineActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (requestCode == createNewRoutineActivityRequestCode
            && resultCode == Activity.RESULT_OK) {
            Toast.makeText(
                applicationContext,
                R.string.success_new_routine,
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                applicationContext,
                R.string.fail_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun cycleWeekdays(view: View) {
        val direction = view.tag.toString().toInt()
        currentDayNumber += direction
        if (currentDayNumber <= 0) {
            currentDayNumber += 7
        } else if (currentDayNumber >= 8){
            currentDayNumber -= 7
        }

        setHeaderText()
        routineViewModel.setRoutinesForWeekday(DayOfWeek.of(currentDayNumber).toString())
    }


    private fun setHeaderText() {
        val textViewToday = findViewById<TextView>(R.id.textViewToday)
        textViewToday.text = DayOfWeek.of(currentDayNumber).toString()
    }

    fun showPopUpMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_main, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.item_delete -> {
                    routineViewModel.delete(view.tag.toString().toInt())
                }
            }
            true
        })
        popupMenu.show()
    }
}