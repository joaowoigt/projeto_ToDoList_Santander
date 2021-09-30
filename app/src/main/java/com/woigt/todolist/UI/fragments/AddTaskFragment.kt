package com.woigt.todolist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.woigt.todolist.data.Task
import com.woigt.todolist.databinding.ActivityAddTaskBinding
import com.woigt.todolist.utils.format
import com.woigt.todolist.utils.text
import com.woigt.todolist.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

/**
 *  Fragment for adding and editing tasks on the RoomDatabase and communicate to the MainActivity.
 *  Users need to enter a task title, description, date and time.
 */
class AddTaskFragment : Fragment() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var task: Task
    private val args: AddTaskFragmentArgs by navArgs()
    private val taskViewModel: TaskViewModel by sharedViewModel()
    private val controler by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = ActivityAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insertListeners()
        changeToEditView()
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
            Toast.makeText(
                context, "Tarefa criada com Sucesso!",
                Toast.LENGTH_SHORT
            ).show()
            controler.popBackStack()
        }
    }

    /**
     * Responsible for changing the add page to an edit page. Retrieve the values of the task and
     * edit then.
     */
    private fun changeToEditView() {
        if (args.taskId != 0) {
            taskViewModel.retrieveTask(args.taskId).observe(viewLifecycleOwner, { selectedItem ->
               task = selectedItem!!
                bindEditTask(task)
                binding.btCreateTask.visibility = View.GONE
                binding.btEditTask.visibility = View.VISIBLE
                binding.tvTitle.text = "Editar Tarefa"
            })
        }
    }

    private fun bindEditTask(task: Task) {
        binding.apply {
            inputTitle.text = task.title.toString()
            inputDescription.text = task.description.toString()
            inputDate.text = task.date.toString()
            inputTime.text = task.time.toString()
        }
    }

    private fun editTask() {
        taskViewModel.editTask(
            task,
            binding.inputTitle.text,
            binding.inputDescription.text,
            binding.inputDate.text,
            binding.inputTime.text
        )
        Toast.makeText(
            context, "Tarefa editada com Sucesso!",
            Toast.LENGTH_SHORT
        ).show()
        controler.popBackStack()
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
            datePicker.show(parentFragmentManager, DATE_PICKER_TAG)
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
            timePicker.show(parentFragmentManager, TIME_PICKER_TAG)
        }

        /**
         * Cancel the activity
         */
        binding.btCancel.setOnClickListener {
            controler.popBackStack()
        }

        /**
         * Add task
         */
        binding.btCreateTask.setOnClickListener {
            addNewItem()
        }

        binding.btEditTask.setOnClickListener {
            editTask()
        }
    }
}
