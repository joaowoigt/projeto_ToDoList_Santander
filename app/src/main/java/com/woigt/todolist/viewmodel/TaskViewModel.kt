package com.woigt.todolist.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woigt.todolist.data.Task
import com.woigt.todolist.data.source.TaskRepository
import kotlinx.coroutines.launch

/**
 * ViewModel used in all Activities
 */
class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<Task>> = taskRepository.allTask

    private fun insertTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

     fun retrieveTask(id: Int?): LiveData<Task?> {
            return taskRepository.retrieveTask(id)
    }

    /**
     * getNewTaskEntry, addNewTask an isEntryValid are auxiliaries functions to access insertTask,
     * preventing null camps to reach de database
     */
    private fun getNewTaskEntry(title: String, description: String, date: String, time: String)
    :Task {
        return Task(title, description, date, time)
    }

    fun addNewTask(title: String, description: String, date: String, time: String) {
        val newTask = getNewTaskEntry(title, description, date, time)
        insertTask(newTask)
    }

    fun isEntryValid(title: String, description: String, date: String, time: String): Boolean {
        if (title.isBlank()  || description.isBlank() || date.isBlank() || time.isBlank() ) {
            return false
        }
        return true
    }

     private fun updateItem(task: Task) {
        viewModelScope.launch {
            taskRepository.updateItem(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun editTask(task: Task,title: String, description: String, date: String, time: String ) {
        val newTask = task.copy(title= title, description= description, date= date, time= time)
        updateItem(newTask)
    }

    fun updateCompleted(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateCompleted(taskId, isCompleted)
        }
    }
}
