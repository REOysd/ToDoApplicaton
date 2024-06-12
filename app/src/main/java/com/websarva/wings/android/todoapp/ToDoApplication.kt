package com.websarva.wings.android.todoapp

import android.app.Application
import com.websarva.wings.android.todoapp.database.toDoDatabase

class ToDoApplication:Application() {
    companion object{
        lateinit var database: toDoDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = toDoDatabase.getDatabase(this)
    }
}