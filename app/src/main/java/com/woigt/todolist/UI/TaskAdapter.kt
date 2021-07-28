//package com.woigt.todolist.ui
//
//import android.database.Cursor
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ListAdapter
//import android.widget.PopupMenu
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.woigt.todolist.R
//import com.woigt.todolist.database.TasksDatabaseHelper.Companion.DATE_TASK
//import com.woigt.todolist.database.TasksDatabaseHelper.Companion.DESCRIPTION_TASK
//import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TIME_TASK
//import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TITLE_TASK
//import com.woigt.todolist.model.TaskClickedListener
//import kotlinx.android.synthetic.main.item_task.view.*
//
//class TaskAdapter: ListAdapter<Task{
//
//
//
//    private var mCursor: Cursor? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
//        TaskViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
//        )
//
//
//    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        mCursor?.moveToPosition(position)
//
//        holder.taskTitle.text = mCursor?.getString(mCursor?.getColumnIndex(TITLE_TASK) as Int)
//        holder.taskDescription.text =
//            mCursor?.getString(mCursor?.getColumnIndex(DESCRIPTION_TASK) as Int)
//        holder.taskDate.text = mCursor?.getString(mCursor?.getColumnIndex(DATE_TASK) as Int)
//        holder.taskTime.text = mCursor?.getString(mCursor?.getColumnIndex(TIME_TASK) as Int)
//
//        holder.taskDetail.setOnClickListener { listener.taskClickedItem(mCursor as Cursor) }
//        holder.taskEdit.setOnClickListener { listener.taskEditItem(mCursor as Cursor) }
//        holder.taskDelete.setOnClickListener { listener.taskRemoveItem(mCursor as Cursor) }
//
//
//    }
//
//
//    override fun getItemCount(): Int = if (mCursor != null) mCursor?.count as Int else 0
//
//    fun setCursor(newCursor: Cursor?) {
//        mCursor = newCursor
//        notifyDataSetChanged()
//    }
//
//
//    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val taskTitle = itemView.tv_task_title as TextView
//        val taskDescription = itemView.tv_task_description as TextView
//        val taskDate = itemView.tv_task_date as TextView
//        val taskTime = itemView.tv_task_time as TextView
//        val taskDetail = itemView.bt_detail
//        val taskEdit = itemView.bt_edit
//        val taskDelete = itemView.bt_delete
//
//    }
//}