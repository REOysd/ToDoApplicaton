package com.websarva.wings.android.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [toDoEntity::class,createTemplate::class], version = 1, exportSchema = false)
abstract class toDoDatabase: RoomDatabase() {
    abstract fun ToDoDao():toDoDao

    companion object{
        @Volatile
        private var INSTANCE :toDoDatabase? = null

        fun getDatabase(context: Context):toDoDatabase{
            return INSTANCE?: synchronized(this){
                Room.databaseBuilder(
                    context = context,
                    klass = toDoDatabase::class.java,
                    name = "toDoDatabase"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}