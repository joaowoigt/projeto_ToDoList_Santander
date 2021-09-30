package com.woigt.todolist.ui.taskAdapter


import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.woigt.todolist.R
import com.woigt.todolist.data.Task
import com.woigt.todolist.databinding.ItemTaskBinding
import com.woigt.todolist.viewmodel.TaskViewModel

/**
 * Adapter for the task list on the MainActivity. Has a reference to the [TaskViewModel]
 * to implement the setStyle with the correct behavior.
 */
class TaskListAdapter(
    private val context: Context,
    var taskViewModel: TaskViewModel,
    var onItemClicked: (task: Task) -> Unit = {}
) : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    inner class TaskViewHolder(private val viewDataBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {


        private lateinit var task: Task


        fun bind(task: Task) {
            this.task = task
            viewDataBinding.task = task

            setStyle(task)

            this.viewDataBinding.checkbox.setOnClickListener {
                if (task.isCompleted == false) {
                    taskViewModel.updateCompleted(task.id, true)
                } else {
                    taskViewModel.updateCompleted(task.id, false)
                }
            }
        }


        /**
         * Paint a line on the task to give de user the experience that it's done
         */
        private fun setStyle(item: Task) {
            if (item.isCompleted == true) {
                viewDataBinding.tvTaskTitle.paintFlags = viewDataBinding.tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                viewDataBinding.tvTaskDescription.paintFlags =
                    viewDataBinding.tvTaskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                viewDataBinding.tvTaskDate.paintFlags = viewDataBinding.tvTaskDate.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                viewDataBinding.tvTaskTime.paintFlags = viewDataBinding.tvTaskTime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            } else {
                viewDataBinding.tvTaskTitle.paintFlags =  viewDataBinding.tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                viewDataBinding.tvTaskDescription.paintFlags =
                    viewDataBinding.tvTaskDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                viewDataBinding.tvTaskDate.paintFlags = viewDataBinding.tvTaskDate.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                viewDataBinding.tvTaskTime.paintFlags = viewDataBinding.tvTaskTime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        }
    /**
     * Callback for calculation the diff between two non-null items in a list.
     */
     object TaskComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title
        }

    }

    }


