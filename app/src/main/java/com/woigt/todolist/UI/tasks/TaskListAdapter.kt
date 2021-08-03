package com.woigt.todolist.ui.tasks


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
import com.woigt.todolist.viewmodel.TaskViewModel

/**
 * Adapter for the task list on the MainActivity. Has a reference to the [TaskViewModel]
 * to implement the setStyle with the correct behavior.
 */
class TaskListAdapter(val onItemClicked: (Task) -> Unit,
                        val taskViewModel: TaskViewModel) :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.tv_task_title)
        private val taskDescription: TextView = itemView.findViewById(R.id.tv_task_description)
        private val taskDate: TextView = itemView.findViewById(R.id.tv_task_date)
        private val taskTime: TextView = itemView.findViewById(R.id.tv_task_time)
        private val taskButtonCheck: ImageButton = itemView.findViewById(R.id.checkbox)


        fun bind(item: Task) {
            taskTitle.text = item.title
            taskDescription.text = item.description
            taskDate.text = item.date
            taskTime.text = item.time

            setStyle(item)

            this.taskButtonCheck.setOnClickListener {
                if (item.isCompleted == false) {
                    taskViewModel.updateCompleted(item.id, true)
                }else {
                    taskViewModel.updateCompleted(item.id, false)
                }
            }
        }

        /**
         * Paint a line on the task to give de user the experience that it's done
         */
        private fun setStyle(item: Task) {
            if (item.isCompleted == true) {
                taskTitle.paintFlags = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                taskDescription.paintFlags =
                    taskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                taskDate.paintFlags = taskDate.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                taskTime.paintFlags = taskTime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            } else {
                taskTitle.paintFlags = taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                taskDescription.paintFlags =
                    taskDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                taskDate.paintFlags = taskDate.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                taskTime.paintFlags = taskTime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    /**
     * Callback for calculation the diff between two non-null items in a list.
     */
    class TaskComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title
        }

    }
}
