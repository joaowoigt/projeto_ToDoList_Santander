package com.woigt.todolist.data.source.local


import androidx.room.*
import com.woigt.todolist.data.Task
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the task_table
 */
@Dao
interface TaskDao {

    /**
     * @return all tasks
     */
    @Query("SELECT * FROM task_table")
    fun getTasks(): Flow<List<Task>>

    /**
     * Insert a task in the database. Replace if it already exists
     *
     * @param task the task to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    /**
     * Update a task
     *
     * @param task the task to be updated
     */
    @Update
    suspend fun update(task: Task)

    /**
     * Delete a task
     *
     * @param task task to be deleted
     */
    @Delete
    suspend fun delete(task: Task)

    /**
     * Select a task by id
     *
     * @param id the task id
     */
    @Query("SELECT * from task_table WHERE id = :id")
    fun getItem(id: Int?): Flow<Task>

    /**
     * Update only the complete status of a task
     *
     * @param taskId id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE task_table SET completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)






}