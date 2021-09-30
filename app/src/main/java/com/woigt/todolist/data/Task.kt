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
    @ColumnInfo(name = "title") var title: String? = "",
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "date") var date: String? = "",
    @ColumnInfo(name = "time") var time: String? = "",
    @ColumnInfo(name= "completed") var isCompleted: Boolean? = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
