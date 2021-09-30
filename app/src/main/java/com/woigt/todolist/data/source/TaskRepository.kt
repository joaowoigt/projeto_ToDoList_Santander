package com.woigt.todolist.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.woigt.todolist.data.Task
import com.woigt.todolist.data.source.local.TaskDao

/**
 *  Repository to the TaskViewModel.
 *
 *  Single entry point for managing tasks' data.
 */
class TaskRepository(private val taskDao: TaskDao) {

    val allTask: LiveData<List<Task>> = taskDao.getTasks().asLiveData()

    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    fun retrieveTask(id: Int?): LiveData<Task?> {
        return taskDao.getItem(id).asLiveData()
    }

    suspend fun updateItem(task: Task) {
        taskDao.update(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    suspend fun updateCompleted(taskId: Int, isCompleted: Boolean) {
        taskDao.updateCompleted(taskId, isCompleted)
    }

}
