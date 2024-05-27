package com.websarva.wings.android.todoapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class toDoEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var title:String,
    var description:String,
    var time:String
)

@Entity(tableName = "createTemplate")
data class createTemplate(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var TemplateTitle:String,
    var TemplateText:String
)




