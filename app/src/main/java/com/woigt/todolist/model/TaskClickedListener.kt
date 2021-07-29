package com.woigt.todolist.model

import android.database.Cursor
import com.woigt.todolist.localdata.Task

interface TaskClickedListener {
    fun taskClickedItem(task: Task)


}