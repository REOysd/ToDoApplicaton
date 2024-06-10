package com.websarva.wings.android.todoapp.compose.GeminiAPI

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GeminiAPIViewModel(): ViewModel() {
    private val _UiState:MutableStateFlow<List<data>> = MutableStateFlow(emptyList())
    val UiState:StateFlow<List<data>> = _UiState.asStateFlow()
    var inputText = mutableStateOf("")

    fun addMessage(message:String,participant:Boolean){
        _UiState.value = _UiState.value + data(text = message, participant = participant)
    }
    
}