package com.woigt.todolist.localdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*


@Entity(tableName = "task_table")
data class Task @JvmOverloads constructor (
    @ColumnInfo(name = "title") val title: String? = "",
    @ColumnInfo(name = "description") val description: String? = "",
    @ColumnInfo(name = "date") val date: String? = "",
    @ColumnInfo(name = "time") val time: String? = "",
    @ColumnInfo(name= "completed") val isCompleted: Boolean? = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
