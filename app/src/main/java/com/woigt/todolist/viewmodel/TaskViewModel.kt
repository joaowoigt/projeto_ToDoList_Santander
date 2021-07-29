package com.woigt.todolist.viewmodel

import androidx.lifecycle.*
import com.woigt.todolist.localdata.Task
import com.woigt.todolist.localdata.TaskApplication
import com.woigt.todolist.localdata.TaskDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskViewModel(private val taskDao: TaskDao): ViewModel() {

    val allTasks: LiveData<List<Task>> = taskDao.getTasks().asLiveData()

    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    fun retrieveTask(id: Int): LiveData<Task> {
        return taskDao.getItem(id).asLiveData()
    }

    private fun getNewTaskEntry(title: String, description: String,
                                date: String, time: String) :Task {
        return Task(title, description, date, time)
    }

    fun addNewTask(title: String, description: String,
                   date: String, time: String) {
        val newTask = getNewTaskEntry(title, description, date, time)
        insertTask(newTask)
    }

    fun isEntryValid(title: String, description: String,
                     date: String, time: String): Boolean {
        if (title.isBlank() || description.isBlank() || date.isBlank() || time.isBlank()) {
            return false
        }
        return true
    }

     private fun updateItem(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }

    fun editTask(task: Task,title: String, description: String,
                 date: String, time: String ) {
        val newTask = task.copy(title= title, description= description, date= date, time= time)
        updateItem(newTask)
    }


}

class TaskViewModelFactory(private val taskDao: TaskDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class.")
    }

}