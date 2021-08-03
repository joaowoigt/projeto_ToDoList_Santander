package com.woigt.todolist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Model class for a Task
 *
 *  @param title title of the task
 *  @param description description of the task
 *  @param date Scheduled date for the task
 *  @param time Scheduled time for the task
 *  @param isCompleted Whether or not this task is completed
 */
@Entity(tableName = "task_table")
data class Task @JvmOverloads constructor (
    @ColumnInfo(name = "title") val title: String? = "",
    @ColumnInfo(name = "description") val description: String? = "",
    @ColumnInfo(name = "date") val date: String? = "",
    @ColumnInfo(name = "time") val time: String? = "",
    @ColumnInfo(name= "completed") val isCompleted: Boolean? = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
