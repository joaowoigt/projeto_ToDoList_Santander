package com.woigt.todolist.model

import android.database.Cursor

interface TaskClickedListener {
    fun taskClickedItem(cursor: Cursor)
    fun taskRemoveItem(cursor: Cursor?)
    fun taskEditItem(cursor: Cursor)

}