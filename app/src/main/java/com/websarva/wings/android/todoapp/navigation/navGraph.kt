package com.websarva.wings.android.todoapp.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.websarva.wings.android.todoapp.R
import com.websarva.wings.android.todoapp.ToDoApplication
import com.websarva.wings.android.todoapp.compose.ToDoMenu
import com.websarva.wings.android.todoapp.compose.ToDoTemplateMenu
import com.websarva.wings.android.todoapp.compose.ToDoTemplateViewModel
import com.websarva.wings.android.todoapp.compose.TopMenu
import com.websarva.wings.android.todoapp.compose.toDoViewModel
import com.websarva.wings.android.todoapp.database.toDoEntity

@Composable
fun ToDoNavHost(
    navController: NavHostController,
    viewModel: toDoViewModel,
    templateViewModel: ToDoTemplateViewModel
){
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.Home.name
    ){
        composable(NavigationScreen.Home.name){
            TopMenu(
                onClickAddButton = { navController.navigate(NavigationScreen.AddMenu.name) },
                viewModel = viewModel,
                TemplateViewModel = templateViewModel,
                onClickAddButtonToTemplate = {navController.navigate(NavigationScreen.AddTemplateMenu.name)}
                )
        }
        composable(NavigationScreen.AddMenu.name){
            ToDoMenu(
                viewModel = viewModel,
                onClickBackNavigation = {navController.popBackStack()},
            )
        }
        composable(NavigationScreen.AddTemplateMenu.name){
            ToDoTemplateMenu(
                viewModel = templateViewModel,
                onClickBackNavigation = { navController.popBackStack() })
        }
    }
}

enum class NavigationScreen(){
    Home,
    AddMenu,
    AddTemplateMenu
}