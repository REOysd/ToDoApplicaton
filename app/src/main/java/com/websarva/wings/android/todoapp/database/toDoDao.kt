package com.websarva.wings.android.todoapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface toDoDao {

    @Insert
    suspend fun insert(items:toDoEntity)

    @Delete
    suspend fun delete(items: toDoEntity)

    @Update
    suspend fun update(items: toDoEntity)

    @Query("SELECT * FROM toDoEntity")
    fun getAll():Flow<List<toDoEntity>>

    @Query("SELECT * FROM toDoEntity ORDER BY time DESC")
    fun getAllByTime():Flow<List<toDoEntity>>

    @Insert
    suspend fun insertTemplate(template: createTemplate)

    @Delete
    suspend fun deleteTemplate(template: createTemplate)

    @Update
    suspend fun updateTemplate(template: createTemplate)

    @Query("SELECT * FROM createTemplate")
    fun getAllTemplate():Flow<List<createTemplate>>
}