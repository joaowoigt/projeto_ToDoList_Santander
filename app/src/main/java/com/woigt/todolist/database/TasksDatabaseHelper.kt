package com.woigt.todolist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class TasksDatabaseHelper(
    context: Context
): SQLiteOpenHelper(context, "databaseTasks", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_TASKS (" +
                "$_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "$TITLE_TASK TEXT NOT NULL," +
                "$DESCRIPTION_TASK TEXT NOT NULL," +
                "$DATE_TASK TEXT NOT NULL," +
                "$TIME_TASK TEXT NOT NULL," +
                "$COMPLETED_TASK INTEGER NOT NULL DEFAULT 0)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        const val TABLE_TASKS: String = "Tasks"
        const val TITLE_TASK: String = "title"
        const val DESCRIPTION_TASK: String = "description"
        const val DATE_TASK: String = "date"
        const val TIME_TASK: String = "time"
        const val COMPLETED_TASK: String = "completed"
    }

}