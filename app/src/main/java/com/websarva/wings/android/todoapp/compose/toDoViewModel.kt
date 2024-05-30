package com.websarva.wings.android.todoapp.compose

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.android.todoapp.ToDoApplication
import com.websarva.wings.android.todoapp.database.toDoEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class toDoViewModel:ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var settingTemplate by mutableStateOf(false)
    var changeSaveToUpdate by mutableStateOf(false)
    var changeNormalToSorted by mutableStateOf(true)
    private val _data = MutableStateFlow(System.currentTimeMillis())
    val data:StateFlow<Long> = _data.asStateFlow()
    val formattedTimer:StateFlow<String> = data.map {data ->
        val formatter = SimpleDateFormat("MM/dd:HH:mm")
        formatter.format(data)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),"")
    var settingData:toDoEntity? = null

    init {
        viewModelScope.launch {
            while (true){
                _data.value = System.currentTimeMillis()
                delay(1000)
            }
        }
    }

    val toDoListState: StateFlow<ToDoListState> = ToDoApplication.database.ToDoDao().getAll().map { ToDoListState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ToDoListState(emptyList())
        )

    val toDoListStateAsSortTime: StateFlow<ToDoListState> = ToDoApplication.database.ToDoDao().getAllByTime().map { ToDoListState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ToDoListState(emptyList())
        )

    fun clearItem(boolean: Boolean){
        if(!boolean){
            title = ""
            description = ""
        }
    }

    fun insertToDoItem(toDoItem:toDoEntity) = viewModelScope.launch {
        ToDoApplication.database.ToDoDao().insert(toDoItem)
    }

    fun deleteToDoItem(toDoItem: toDoEntity) = viewModelScope.launch {
        ToDoApplication.database.ToDoDao().delete(toDoItem)
    }

    fun setToDoItem(toDoItem: toDoEntity){
        settingData = toDoItem
        title = toDoItem.title
        description = toDoItem.description
    }

    fun updateToDoItem(toDoItem: toDoEntity) = viewModelScope.launch {
        settingData?.let {
            ToDoApplication.database.ToDoDao().update(toDoItem)
        }
    }

    fun ChangeToUpdated(boolean: Boolean,time:String){
        if (boolean){
            insertToDoItem(
                toDoEntity(title = title,
                    description = description,
                    time = time
                )
            )
        }else{
            settingData?.let {
                updateToDoItem(toDoEntity(it.id,title,description,time))
                Log.d("tag","updateToDo")
            }
        }
    }
}

data class ToDoListState(val ToDoList:List<toDoEntity>)