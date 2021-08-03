package com.woigt.todolist.ui.tasks



import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Events.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.woigt.todolist.R
import com.woigt.todolist.databinding.ActivityDetailBinding
import com.woigt.todolist.data.Task
import com.woigt.todolist.data.source.local.TaskApplication
import com.woigt.todolist.viewmodel.TaskViewModel
import com.woigt.todolist.viewmodel.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Main UI for the task detail screen.
 */
class DetailActivity: AppCompatActivity() {

    lateinit var task: Task

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    private fun deleteTask() {
        taskViewModel.deleteTask(task)
        Toast.makeText(this,"Deletado com Sucesso!", Toast.LENGTH_LONG).show()
        finish()

    }

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra("id", 0)

         fun bind(task: Task?) {
             binding.apply {
                 tv_detail_title.text = task?.title ?: ""
                 tv_detail_description.text = task?.description ?: ""
                 tv_detail_date.text = task?.date ?: ""
                 tv_detail_time.text = task?.time ?: ""

             }
         }

            taskViewModel.retrieveTask(id).observe(this,   { selectedItem ->
                if (selectedItem != null) {
                    task = selectedItem
                    bind(task)
                } else {
                    finish()
                }
            })

            insertListeners()
    }

    private fun insertListeners() {
        /**
         * Navigate to [AddEditTaskActivity] sending the necessary extra to change the page
         */
        bt_edit.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            intent.putExtra("id", task.id)
            startActivity(intent)
        }

        /**
         * Cancel the activity
         */
        bt_back.setOnClickListener {
            finish()
        }

        /**
         * Delete the task
         */
        bt_delete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Excluir tarefa?")
            builder.setMessage(R.string.alertdialog)
            builder.setPositiveButton("Sim") { _, _ ->
                Toast.makeText(
                    this, "Tarefa excluida com Sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
                deleteTask()
            }
            builder.setNegativeButton("Cancelar") { _, _ -> finish() }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        /**
         * Navigate to the schedule page from the native Android Agenda
         */
        bt_schedule.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT)
                    .setData(CONTENT_URI)
                    .putExtra(TITLE, task.title)
                    .putExtra(DESCRIPTION, task.description)
            startActivity(intent)
            }

        }
}
