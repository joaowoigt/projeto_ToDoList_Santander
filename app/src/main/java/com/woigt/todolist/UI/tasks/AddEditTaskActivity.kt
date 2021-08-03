package com.woigt.todolist.ui.tasks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.woigt.todolist.databinding.ActivityAddTaskBinding
import com.woigt.todolist.utils.format
import com.woigt.todolist.utils.text
import com.woigt.todolist.data.Task
import com.woigt.todolist.data.source.local.TaskApplication
import com.woigt.todolist.ui.MainActivity
import com.woigt.todolist.viewmodel.TaskViewModel
import com.woigt.todolist.viewmodel.TaskViewModelFactory
import java.util.*

/**
 *  Activity for adding and editing tasks on the RoomDatabase and communicate to the MainActivity.
 *  Users need to enter a task title, description, date and time.
 */
class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }
    lateinit var task: Task

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Responsible for changing the add page to a edit page. Retrieve the values of the task and
         * edit then.
         */
        fun bindEditTask(task: Task) {
            binding.apply {
                binding.inputTitle.text = task.title.toString()
                binding.inputDescription.text = task.description.toString()
                binding.inputDate.text = task.date.toString()
                binding.inputTime.text = task.time.toString()
            }
        }

        if (intent.hasExtra("id")) {
            val id = intent.getIntExtra("id", 0)
            taskViewModel.retrieveTask(id).observe(this, {
                selectedItem -> task = selectedItem!!
                bindEditTask(task)
                binding.btCreateTask.visibility = View.GONE
                binding.btEditTask.visibility = View.VISIBLE
                binding.tvTitle.text = "Editar Tarefa"
            })
        }

        insertListeners()

    }

    private fun isEntryValid(): Boolean {
        return taskViewModel.isEntryValid(
            binding.inputTitle.text,
            binding.inputDescription.text,
            binding.inputDate.text,
            binding.inputTime.text,
        )

    }

    private fun addNewItem() {
        if (isEntryValid()) {
            taskViewModel.addNewTask(
                binding.inputTitle.text,
                binding.inputDescription.text,
                binding.inputDate.text,
                binding.inputTime.text
            )
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insertListeners() {
        /**
         * Date picker based on material design
         */
        binding.inputDate.editText?.setOnClickListener {
          val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
               val timeZone = TimeZone.getDefault()
               val offset = timeZone.getOffset(Date().time) * -1
               binding.inputDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, DATE_PICKER_TAG)


        }

        /**
         * Time picker based on material design
         */
        binding.inputTime.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12).setMinute(45).setTitleText("Hora da tarefa").build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9)
                                    "0${timePicker.minute}"
                                    else timePicker.minute
                val hour = if (timePicker.hour in 0..9)
                                    "0${timePicker.hour}"
                                    else timePicker.hour

                binding.inputTime.text =  "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, TIME_PICKER_TAG)
        }
        /**
         * Cancel the activity
         */
        binding.btCancel.setOnClickListener {
            finish()
        }

        /**
         * Add task
         */
        binding.btCreateTask.setOnClickListener {
            addNewItem()
        }

        binding.btEditTask.setOnClickListener {
            taskViewModel.editTask(task,
                binding.inputTitle.text,
                binding.inputDescription.text,
                binding.inputDate.text,
                binding.inputTime.text
            )
            finish()
        }


    }

    /**
     * Keys for navigation
     */
    companion object {
        const val DATE_PICKER_TAG = "DATE_PICKER_TAG"
        const val TIME_PICKER_TAG = "TIME_PICKER_TAG"
        const val EXTRA_TITLE = "com.woigt.todolist.tasklistsql.TITLE"
        const val EXTRA_DESCRIPTION = "com.woigt.todolist.tasklistsql.DESCRIPTION"
        const val EXTRA_DATE = "com.woigt.todolist.tasklistsql.DATE"
        const val EXTRA_TIME = "com.woigt.todolist.tasklistsql.TIME"
        const val EXTRA_COMPLETED = "com.woigt.todolist.tasklistsql.COMPLETED"
    }
}
