package com.woigt.todolist.localdata

import android.app.Application

class TaskApplication: Application() {

    val database: TaskRoomDatabase by lazy { TaskRoomDatabase.getDatabase(this) }

}