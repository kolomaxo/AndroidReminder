package com.example.reminder


import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate


class CreateNewRoutineActivity : AppCompatActivity() {
    val weekdayButtons = mutableListOf<Button>()
    val selectedButtonBackground = Color.rgb(233, 30, 99)
    //val availableButtonBackground = Color.rgb(103, 58, 183)
    val weekdays = BooleanArray(7)

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
}