package com.websarva.wings.android.todoapp.compose.GeminiAPI

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.websarva.wings.android.todoapp.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeminiAPIViewModel(): ViewModel() {
    private val _UiState:MutableStateFlow<List<data>> = MutableStateFlow(emptyList())
    val UiState:StateFlow<List<data>> = _UiState.asStateFlow()
    var inputText by mutableStateOf("")

    fun addMessage(message:String,participant:Boolean){
        _UiState.value = _UiState.value + data(text = message, participant = participant)
    }

    suspend fun RunGemini(
        viewModel:GeminiAPIViewModel,
        text:String,
        participant: Boolean
    ){
        try{
            val model = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = BuildConfig.apiKey
            )
            val response = model.generateContent(text)
            Log.d("response",response.text.toString())
            viewModel.addMessage(response.text.toString(), participant = participant)

        }catch (e:Exception){
            val text = "申し訳ございません。"+
                    "ネットワークに接続されていません。" +
                    "Wi- Fiまたはモバイルデータ通信が有効になっているか確認してください"
            viewModel.addMessage(text,true)
            Log.e("Error",e.toString())
        }
    }

    fun onClickKeyboardDone(
        scope:CoroutineScope,
        viewModel: GeminiAPIViewModel,
        string: String,
        participant: Boolean
    ){
        scope.launch {
            viewModel.RunGemini(viewModel,string, participant = participant)
        }
    }
}