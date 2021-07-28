package com.woigt.todolist.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.woigt.todolist.R
import com.woigt.todolist.database.TaskProvider.Companion.URI_TASKS
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.DATE_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.DESCRIPTION_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TIME_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TITLE_TASK
import kotlinx.android.synthetic.main.task_edit_dialog.*

class TaskEditFragment: DialogFragment(), DialogInterface.OnClickListener {

    private var id: Long = 0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.task_edit_dialog, null)

        id = arguments?.getLong(EXTRA_ID) as Long
        val uri = Uri.withAppendedPath(URI_TASKS, id.toString())
        val cursor = activity?.contentResolver?.query(uri, null,
            null, null, null)

        if (cursor?.moveToNext() as Boolean) {
            edt_dialog_title.setText(cursor?.getString(cursor.getColumnIndex(TITLE_TASK)))
            edt_dialog_description.setText(cursor?.getString(cursor.getColumnIndex(DESCRIPTION_TASK)))
            edt_dialog_date.setText(cursor?.getString(cursor.getColumnIndex(DATE_TASK)))
            edt_dialog_time.setText(cursor?.getString(cursor.getColumnIndex(TIME_TASK)))
        }
        cursor.close()

        return AlertDialog.Builder(activity as Activity)
            .setTitle("Editar Tarefa")
            .setPositiveButton("Salver", this)
            .setNegativeButton("Cancelar", this)
            .create()

    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val values = ContentValues()
        values.put(TITLE_TASK, edt_dialog_title.toString())
        values.put(DESCRIPTION_TASK, edt_dialog_description.toString())
        values.put(DATE_TASK, edt_dialog_date.toString())
        values.put(TIME_TASK, edt_dialog_time.toString())

        val uri = Uri.withAppendedPath(URI_TASKS, id.toString())
        context?.contentResolver?.update(uri, values, null, null)
    }


    companion object {
        private const val EXTRA_ID = "id"
        fun newInstance(id: Long): TaskEditFragment {
            val bundle = Bundle()
            bundle.putLong(EXTRA_ID, id)

            val taskFragment = TaskEditFragment()
            taskFragment.arguments = bundle
            return taskFragment
        }
    }
}