package com.example.reminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completred_routines_table")
data class CompletedRoutine (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "routineId") val routineId: String,
    @ColumnInfo(name = "date") val date: Int
)
