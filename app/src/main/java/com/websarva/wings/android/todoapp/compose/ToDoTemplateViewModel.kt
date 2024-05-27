package com.websarva.wings.android.todoapp.compose

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.android.todoapp.ToDoApplication
import com.websarva.wings.android.todoapp.database.createTemplate
import com.websarva.wings.android.todoapp.database.toDoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ToDoTemplateViewModel:ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var changeSaveToUpdate by mutableStateOf(true)

    val toDoTemplateListState: StateFlow<ToDoTemplateListState> = ToDoApplication.database.ToDoDao().getAllTemplate().map {
        ToDoTemplateListState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ToDoTemplateListState(emptyList())
    )

    fun insertToDoTemplate(template: createTemplate) = viewModelScope.launch {
        ToDoApplication.database.ToDoDao().insertTemplate(template)
    }

    fun updateToDoItemTemplate(template: createTemplate) = viewModelScope.launch {
    }

    fun deleteToDoTemplate(template: createTemplate) = viewModelScope.launch {
        ToDoApplication.database.ToDoDao().deleteTemplate(template)
    }

    fun ChangeToUpdatedOnTemplate(boolean: Boolean){
        if (boolean){
            insertToDoTemplate(
                createTemplate(TemplateTitle = title, TemplateText = description)
            )
//        }else{
//            settingData?.let {
//                updateToDoItemTemplate(toDoEntity(it.id,title,description,time))
//                Log.d("tag","updateToDo")
//            }
        }
    }
}

data class ToDoTemplateListState(val templatesList:List<createTemplate>)