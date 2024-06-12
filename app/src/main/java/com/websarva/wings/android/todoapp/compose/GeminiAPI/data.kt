package com.websarva.wings.android.todoapp.compose.GeminiAPI

import java.util.UUID

data class data(
    val id:String = UUID.randomUUID().toString(),
    val text:String,
    val participant:Boolean
)
