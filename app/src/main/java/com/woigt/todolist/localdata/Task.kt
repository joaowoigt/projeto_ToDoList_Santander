package com.woigt.todolist.localdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "task_table")
class Task @JvmOverloads constructor(
    @ColumnInfo(name = "title") var title: String? = "",
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "date") var date: String? = "",
    @ColumnInfo(name = "time") var time: String? = "",
    @ColumnInfo(name= "completed") var isCompleted: Boolean? = false,
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()

)
