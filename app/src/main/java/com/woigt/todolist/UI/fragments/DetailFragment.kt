package com.woigt.todolist.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.woigt.todolist.data.Task
import com.woigt.todolist.databinding.ActivityDetailBinding
import com.woigt.todolist.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**+
 * Main UI for the task detail screen.
 */
class DetailFragment : Fragment() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var task: Task
    private val args: DetailFragmentArgs by navArgs()
    private val taskViewModel: TaskViewModel by sharedViewModel()
    private val controler by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = ActivityDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveAndBindTask(args.taskId)
        insertListeners()
    }

    private fun retrieveAndBindTask(id: Int) {
        taskViewModel.retrieveTask(id).observe(viewLifecycleOwner,   { selectedItem ->
            if (selectedItem != null) {
                task = selectedItem
                bindTaskToView(task)
            } else {
                controler.popBackStack()
            }
        })
    }

    private fun bindTaskToView(task: Task) {
        binding.apply {
            tvDetailTitle.text = task.title
            tvDetailDescription.text = task.description
            tvDetailDate.text = task.date
            tvDetailTime.text = task.time
        }
    }

    private fun insertListeners() {
        /**
         * Navigate to [AddTaskFragment] sending the necessary extra to change the page
         */
        binding.btEdit.setOnClickListener {
            val directions = DetailFragmentDirections.actionDetailFragmentToAddFragment(args.taskId)
            controler.navigate(directions)
        }

        /**
         * Cancel the activity
         */
        binding.btBack.setOnClickListener {
            controler.popBackStack()
        }

        /**
         * Delete the task
         */
        binding.btDelete.setOnClickListener {
            buildAlertAndDeleteTask()
        }

        /**
         * Navigate to the schedule page from the native Android Agenda
         */
        bt_schedule.setOnClickListener {
            scheduleTask()
        }
    }

    private fun scheduleTask() {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.Events.TITLE, task.title)
            .putExtra(CalendarContract.Events.DESCRIPTION, task.description)
        startActivity(intent)
    }

    /**
     * Function to build the dialog to delete the task
     */
    private fun buildAlertAndDeleteTask() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Excluir tarefa?")
        builder.setMessage("Deseja mesmo excluir esta tarefa?")
        builder.setPositiveButton("Sim") { _, _ ->
            Toast.makeText(
                context, "Tarefa excluida com Sucesso!",
                Toast.LENGTH_LONG
            ).show()
            deleteTask()
        }
        builder.setNegativeButton("Cancelar") { _, _ -> controler.popBackStack() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun deleteTask() {
        taskViewModel.deleteTask(task)
        controler.popBackStack()
    }
}
