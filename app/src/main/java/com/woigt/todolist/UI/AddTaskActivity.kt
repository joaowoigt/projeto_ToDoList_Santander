package com.woigt.todolist.UI

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.woigt.todolist.databinding.ActivityAddTaskBinding
import com.woigt.todolist.datasource.TaskDataSource
import com.woigt.todolist.extensions.format
import com.woigt.todolist.extensions.text
import com.woigt.todolist.model.Task
import java.util.*

class AddTaskActivity :AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
           val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.inputTitle.text = it.title
                binding.inputDescription.text = it.description
                binding.inputDate.text = it.date
                binding.inputTime.text = it.time
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        // Escolher Data
        binding.inputDate.editText?.setOnClickListener {
          val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
               val timeZone = TimeZone.getDefault()
               val offset = timeZone.getOffset(Date().time) * -1
               binding.inputDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, DATE_PICKER_TAG)
        }

        // Escolher Horario
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
        //Botão de cancerlar
        binding.btCancel.setOnClickListener {
            finish()
        }

        // Botão de adicionar tarefa
        binding.btCreateTask.setOnClickListener {
            val task = Task(
                title = binding.inputTitle.text,
                description = binding.inputDescription.text,
                date = binding.inputDate.text,
                time = binding.inputTime.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }


    }

    companion object {
        const val DATE_PICKER_TAG = "DATE_PICKER_TAG"
        const val TIME_PICKER_TAG = "TIME_PICKER_TAG"
        const val TASK_ID = "task_id"
    }
}