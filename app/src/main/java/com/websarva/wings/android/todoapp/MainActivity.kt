package com.websarva.wings.android.todoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.ai.client.generativeai.BuildConfig
import com.websarva.wings.android.todoapp.compose.ToDoTemplateViewModel
import com.websarva.wings.android.todoapp.compose.TopMenu
import com.websarva.wings.android.todoapp.compose.toDoViewModel
import com.websarva.wings.android.todoapp.navigation.ToDoNavHost
import com.websarva.wings.android.todoapp.ui.theme.ToDoAppTheme
import java.security.Provider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel:toDoViewModel by viewModels()
        val TemplateViewModel:ToDoTemplateViewModel by viewModels()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                val navController:NavHostController = rememberNavController()
                ToDoNavHost(navController = navController, viewModel = viewModel, templateViewModel = TemplateViewModel)
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
    }
}