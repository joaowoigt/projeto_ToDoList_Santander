package com.woigt.todolist.data.source.local

import android.app.Application
import com.woigt.todolist.data.source.TaskRepository

/**
 * Instantiate the RoomDatabase and the repository
 */
class TaskApplication: Application() {

    val database: TaskRoomDatabase by lazy { TaskRoomDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }

}