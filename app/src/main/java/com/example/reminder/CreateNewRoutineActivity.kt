package com.example.reminder


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.DayOfWeek
import java.time.LocalDate
import androidx.lifecycle.ViewModelProvider


class CreateNewRoutineActivity : AppCompatActivity() {
    private val weekdayButtons = mutableListOf<Button>()
    private val selectedButtonBackground = Color.rgb(233, 30, 99)
    //val availableButtonBackground = Color.rgb(103, 58, 183)
    private val weekdays = BooleanArray(7)
    private lateinit var routineViewModel: RoutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_routine)

        if (weekdayButtons.size == 0) {  // new array to track activated weekdays buttons
            weekdayButtons.addAll(arrayOf<Button>(
                    findViewById(R.id.buttonMon),
                    findViewById(R.id.buttonTue),
                    findViewById(R.id.buttonWed),
                    findViewById(R.id.buttonThu),
                    findViewById(R.id.buttonFri),
                    findViewById(R.id.buttonSat),
                    findViewById(R.id.buttonSun)
            ))
        }

        val weekdayButtonListener = View.OnClickListener { // on click flip button stance and update tracking array
            val tmp = it.getBackground()
            with(it as Button) {
                val clickedWeekdayIndex = weekdayButtons.indexOf(this)
                flipWeekdayState(clickedWeekdayIndex)
            }
        }

        for (button in weekdayButtons) {
            button.setOnClickListener(weekdayButtonListener)
            //button.background.setTint(availableButtonBackground)
        }

        val  todayWeekdayIndex = LocalDate.now().dayOfWeek.value - 1
        flipWeekdayState(todayWeekdayIndex) //auto click today
    }


    fun flipWeekdayState(weekdayIndex: Int) = if (!weekdays[weekdayIndex]) {
        val tmp = weekdayButtons[weekdayIndex].background
        weekdayButtons[weekdayIndex].background.setTint(selectedButtonBackground)
        weekdays[weekdayIndex] = true
    } else {
        val a = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimary, a, true)
        weekdayButtons[weekdayIndex].background.setTint(a.data)  // works only for colours, not resources.
        weekdays[weekdayIndex] = false
    }

    fun commitNewActivity(view: View) {
        val replyIntent = Intent()
        val editActivityName = findViewById<TextView>(R.id.editNewRoutineName)
        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        if(TextUtils.isEmpty(editActivityName.text) || !weekdays.any()) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            val activityName = editActivityName.text.toString()
            for (weekdayIndex in 0..6) {
                if (weekdays[weekdayIndex]) {
                    val dayName = DayOfWeek.of(weekdayIndex + 1).toString()
                    val newRoutine = Routine(0, activityName, dayName)
                    routineViewModel.insert(newRoutine)
                }
            }
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }

}

/*
*            intentData?.let { data ->
                val word = Routine(data.getStringExtra(CreateNewRoutineActivity.EXTRA_REPLY))
                wordViewModel.insert(word)
            }
 */