package com.woigt.todolist.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.woigt.todolist.data.Task

/**
 *  The Room database that contains the task_table
 */
@Database(entities = [Task::class], version = 1, exportSchema = false)
 abstract class TaskRoomDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getDatabase(context: Context): TaskRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    TaskRoomDatabase::class.java, "task").build()
                INSTANCE = instance
                instance
            }
        }
    }
 }
