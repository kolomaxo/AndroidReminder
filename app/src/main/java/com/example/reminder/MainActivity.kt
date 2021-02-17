package com.example.reminder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val createNewRoutineActivityRequestCode = 1
    private lateinit var routineViewModel: RoutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    fun showRoutines(view: View) {

    }
}