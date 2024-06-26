package com.websarva.wings.android.todoapp.compose
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.todoapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TopMenu(
    modifier: Modifier = Modifier,
    onClickAddButton:() -> Unit,
    onClickAddButtonToTemplate:() -> Unit,
    viewModel: toDoViewModel,
    TemplateViewModel:ToDoTemplateViewModel
){

    val toDoListState = viewModel.toDoListState.collectAsState()
    val sortedToDoListState = viewModel.toDoListStateAsSortTime.collectAsState()
    val toDoTemplateListState = TemplateViewModel.toDoTemplateListState.collectAsState()


    viewModel.formattedTimer.collectAsState().value.let { formattedTimer ->
        Scaffold(
            topBar = {
                AppBar_Screen(
                    viewModel = viewModel
                )
            }
        ){
                innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ,
            ){
                ModalBottomSheetSample(
                    TemplateList = toDoTemplateListState.value.templatesList,
                    TemplateViewModel = TemplateViewModel,
                    onClickAddButtonToTemplate = onClickAddButtonToTemplate,
                    viewModel = viewModel,
                )
                Row(modifier = modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(top = 30.dp)
                ) {
                    createToDo(
                        itemsList = toDoListState.value.ToDoList,
                        sortedItemsList = sortedToDoListState.value.ToDoList,
                        boolean = viewModel.changeNormalToSorted,
                        onClickDeleteButton = {viewModel.deleteToDoItem(it)},
                        onClickItemButton = {
                            viewModel.changeSaveToUpdate = false
                            viewModel.setToDoItem(it)
                            onClickAddButton()
                        },
                        modifier = Modifier
                    )

                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 100.dp, end = 50.dp)
                ) {
                    ClickButton(onClick = {onClickAddButton()}, viewModel = viewModel)
                }
            }
        }
    }
    }


@Composable
fun ClickButton(
    onClick:() -> Unit,
    viewModel: toDoViewModel,
){
    var isPressed by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (isPressed) 360f else 0f)

    LargeFloatingActionButton(
        onClick = {
            isPressed = true
            onClick()
            viewModel.clearItem(viewModel.settingTemplate)
            Log.d("tag",viewModel.settingTemplate.toString())
            viewModel.changeSaveToUpdate = true
                  },
        modifier = Modifier
            .size(80.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(30.dp)),
        containerColor = Color.LightGray
        )
    {
        Icon(
            Icons.Default.Create,
            contentDescription = null,
            modifier = Modifier
                .rotate(rotation)
                .size(40.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar_screen(
    onClickBackNavigation: () -> Unit,
    viewModel: toDoViewModel,
    time: String
){
    val scope = rememberCoroutineScope()
    var enable by remember { mutableStateOf(true) }

    TopAppBar(
        title = {},
        modifier = Modifier.background(Color.Blue),
        navigationIcon = {
            IconButton(
                onClick = {
                onClickBackNavigation()
                viewModel.settingTemplate = true
                scope.launch {
                        enable = false
                        delay(3000)
                        enable = true
                }
            },
                enabled = enable
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(
                onClick = {
                onClickBackNavigation()
                viewModel.settingTemplate = true
                viewModel.ChangeToUpdated(viewModel.changeSaveToUpdate,time = time)
                    scope.launch {
                        enable = false
                        delay(3000)
                        enable = true
                    }
            },
                enabled = enable
            ) {
                Icon(imageVector = Icons.Filled.Done, contentDescription = "Share")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar_Screen(viewModel: toDoViewModel){
    TopAppBar(
        title = {},
        navigationIcon = {
            DropDownMenuList(viewModel = viewModel)
        }
    )
}

@Composable
fun DropDownMenuList(viewModel: toDoViewModel){
    var expanded by remember { mutableStateOf(false) }

        IconButton(onClick = {expanded = true}) {
            Icon(imageVector = Icons.Filled.List, contentDescription = "ソート")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.NormalSortedOnTopMenu)) },
                onClick = { viewModel.changeNormalToSorted = true },
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.ReverseSortedOnTopMenu)) },
                onClick = { viewModel.changeNormalToSorted = false }
            )
        }
}



@Preview(showBackground = true)
@Composable
fun TopMenuPreView(){
    TopMenu(onClickAddButton = {}, viewModel = toDoViewModel(), onClickAddButtonToTemplate = {},TemplateViewModel = ToDoTemplateViewModel())
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreView(){
    TopAppBar_screen(onClickBackNavigation = {}, viewModel = toDoViewModel(), time = "")
}