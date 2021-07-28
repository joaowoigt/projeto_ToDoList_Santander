package com.woigt.todolist.ui

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.woigt.todolist.database.TaskProvider.Companion.URI_TASKS
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.COMPLETED_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.DATE_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.DESCRIPTION_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TIME_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TITLE_TASK
import com.woigt.todolist.databinding.ActivityAddTaskBinding
import com.woigt.todolist.extensions.format
import com.woigt.todolist.extensions.text
import com.woigt.todolist.ui.MainActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_detail.*

import java.util.*

class AddTaskActivity :AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var newNote = true
        if (intent.hasExtra(EXTRA_ID)) {
            bt_create_task.visibility = View.GONE
            bt_edit_task.visibility = View.VISIBLE
            val id = intent.getStringExtra(EXTRA_ID)

            val uri = Uri.withAppendedPath(URI_TASKS, id)
            val cursor = contentResolver?.query(uri, null,null, null, null)

            if (cursor?.moveToNext() as Boolean) {
                newNote = false
                binding.inputTitle.text = cursor.getString(cursor.getColumnIndex(TITLE_TASK))
                binding.inputDescription.text = cursor.getString(cursor.getColumnIndex(DESCRIPTION_TASK))
                binding.inputDate.text = cursor.getString(cursor.getColumnIndex(DATE_TASK))
                binding.inputTime.text = cursor.getString(cursor.getColumnIndex(TIME_TASK))
            }

            bt_edit_task.setOnClickListener {
                val values = ContentValues()
                values.put(TITLE_TASK, binding.inputTitle.text.toString())
                values.put(DESCRIPTION_TASK, binding.inputDescription.text.toString())
                values.put(DATE_TASK, binding.inputDate.text.toString())
                values.put(TIME_TASK, binding.inputTime.text.toString())
                values.put(COMPLETED_TASK, 0)
                contentResolver.update(uri, values, null, null)
                finish()

            }


            cursor.close()

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
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.inputTitle.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val extras = Bundle()
                val title = binding.inputTitle.text.toString()
                val description = binding.inputDescription.text.toString()
                val date = binding.inputDate.text.toString()
                val time = binding.inputTime.text.toString()
                val completed = false
                extras.putString(EXTRA_TITLE, title)
                extras.putString(EXTRA_DESCRIPTION, description)
                extras.putString(EXTRA_DATE, date)
                extras.putString(EXTRA_TIME, time)
                extras.putBoolean(EXTRA_COMPLETED, completed)
                replyIntent.putExtras(extras)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }

    companion object {
        const val DATE_PICKER_TAG = "DATE_PICKER_TAG"
        const val TIME_PICKER_TAG = "TIME_PICKER_TAG"
        const val EXTRA_ID = "com.woigt.todolist.tasklistsql.ID"
        const val EXTRA_TITLE = "com.woigt.todolist.tasklistsql.TITLE"
        const val EXTRA_DESCRIPTION = "com.woigt.todolist.tasklistsql.DESCRIPTION"
        const val EXTRA_DATE = "com.woigt.todolist.tasklistsql.DATE"
        const val EXTRA_TIME = "com.woigt.todolist.tasklistsql.TIME"
        const val EXTRA_COMPLETED = "com.woigt.todolist.tasklistsql.COMPLETED"
    }
}