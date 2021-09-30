package com.woigt.todolist.di

import androidx.room.Room
import com.woigt.todolist.data.source.TaskRepository
import com.woigt.todolist.data.source.local.TaskRoomDatabase
import com.woigt.todolist.ui.taskAdapter.TaskListAdapter
import com.woigt.todolist.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DBNAME = "db_koin"

val dbModule = module {
    single<TaskRoomDatabase> {
        Room.databaseBuilder(
            get(),
            TaskRoomDatabase::class.java,
            DBNAME
        ).build()
    }
}

val uiModule = module {
    factory<TaskListAdapter> {TaskListAdapter(get(), get()) }
}

val daoModule = module {
    single { get<TaskRoomDatabase>().taskDao()}
}

val repositoryModule = module {
    single { TaskRepository(get())  }
}

val viewModelModule = module {
    viewModel { TaskViewModel(get()) }
}

val appModule = listOf(
    dbModule,
    uiModule,
    daoModule,
    repositoryModule,
    viewModelModule
)
