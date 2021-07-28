package com.woigt.todolist.localdata

import android.app.Application

class TaskApplication: Application() {

    val database by lazy { TaskRoomDatabase.getDatabase(this) }
    val repository by lazy {TaskRepository(database.taskDao())}
}