package com.example.reminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Routine(
        @PrimaryKey val uid: Int,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "weekDay") val weekDay: String
)

