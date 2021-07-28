package com.woigt.todolist.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.provider.BaseColumns._ID
import android.system.Os.close
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TABLE_TASKS

class TaskProvider : ContentProvider() {

    private lateinit var mUriMatcher: UriMatcher
    private lateinit var dbHelper: TasksDatabaseHelper

    override fun onCreate(): Boolean {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher.addURI(AUTHORITY, "tasks", TASKS)
        mUriMatcher.addURI(AUTHORITY, "tasks/#", TASKS_BY_ID)

        if (context != null) { dbHelper = TasksDatabaseHelper(context as Context) }
        return true
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        if (mUriMatcher.match(uri) == TASKS_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linesAffect: Int = db.delete(TABLE_TASKS, "$_ID =?", arrayOf(uri.lastPathSegment))
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linesAffect
        } else {
            throw UnsupportedSchemeException("Uri inválida para exclusão!")
        }
    }
        // Requisição de imagens e arquivos
    override fun getType(uri: Uri): String? = throw UnsupportedSchemeException("Uri não implementada")

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (mUriMatcher.match(uri) == TASKS) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id = db.insert(TABLE_TASKS, null, values)
            val insertURI = Uri.withAppendedPath(BASE_URI, id.toString())
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return insertURI
        } else {
            throw UnsupportedSchemeException("Uri inválida para inserção!")
        }
    }



    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
         when {
            mUriMatcher.match(uri) == TASKS -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val cursor =
                    db.query(TABLE_TASKS, projection, selection, selectionArgs, null, null, sortOrder)
                cursor.setNotificationUri(context?.contentResolver, uri)
                return cursor
            }
            mUriMatcher.match(uri) == TASKS_BY_ID -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val cursor =
                    db.query(TABLE_TASKS, projection, "$_ID = ?", arrayOf(uri.lastPathSegment), null, null, sortOrder)
                cursor.setNotificationUri(context?.contentResolver, uri)
                return cursor
            }
            else -> {
                throw UnsupportedSchemeException("Uri não implementada.")
            }
        }

    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (mUriMatcher.match(uri) == TASKS_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linesAffect = db.update(TABLE_TASKS, values, "$_ID = ?", arrayOf(uri.lastPathSegment))
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linesAffect
        } else {
            throw UnsupportedSchemeException("Uri inválida para exclusão!")
        }
    }

    companion object {
        const val AUTHORITY = "com.woigt.todolist.provider"
        val BASE_URI = Uri.parse("content://$AUTHORITY")
        val URI_TASKS = Uri.withAppendedPath(BASE_URI, "tasks")

        const val  TASKS = 1
        const val TASKS_BY_ID = 2
    }
}