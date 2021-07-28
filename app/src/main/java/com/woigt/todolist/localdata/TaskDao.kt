package com.woigt.todolist.localdata

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE entryid = :taskId")
    suspend fun getTaskById(taskId: String): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM task_table WHERE entryid = :taskId")
    suspend fun deleteTaskById(taskId: String): Int

    @Query("UPDATE task_table SET completed = :completed WHERE entryid = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    @Update
    suspend fun updateTask(task: Task): Int

    @Query("DELETE FROM task_table WHERE completed = 1")
    suspend fun deleteCompletedTasks(): Int
}