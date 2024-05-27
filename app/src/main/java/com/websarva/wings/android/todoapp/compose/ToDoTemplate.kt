package com.websarva.wings.android.todoapp.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.websarva.wings.android.todoapp.R

@Composable
fun ToDoTemplateMenu(
    viewModel: ToDoTemplateViewModel,
    onClickBackNavigation:() -> Unit,
) {
        Scaffold(
            topBar = {
                TopAppBarOnTemplate(
                    onClickBackNavigation = onClickBackNavigation,
                    modifier = Modifier.padding(bottom = 20.dp),
                    viewModel = viewModel
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(innerPadding)
                        .padding(start = 20.dp, end = 20.dp)

                ) {
                    OutlinedTextField(
                        value = viewModel.title,
                        onValueChange = { viewModel.title = it },
                        label = { Text(text = stringResource(id = R.string.createTemplate_title)) },
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxWidth(),

                        )

                }

                Row(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(top = 90.dp, start = 2.dp, end = 2.dp)
                ) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                }

                Row(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(top = 110.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .align(Alignment.Center)
                ) {
                    OutlinedTextField(
                        value = viewModel.description,
                        onValueChange = { viewModel.description = it },
                        shape = RoundedCornerShape(10.dp),
                        label = { Text(text = stringResource(id = R.string.createTemplate_Text)) },
                        singleLine = false,
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarOnTemplate(
    onClickBackNavigation: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ToDoTemplateViewModel,
){
    TopAppBar(
        title = {},
        modifier = Modifier.background(Color.Blue),
        navigationIcon = {
            IconButton(onClick = {
                onClickBackNavigation()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {
                onClickBackNavigation()
                viewModel.ChangeToUpdatedOnTemplate(viewModel.changeSaveToUpdate)
            }) {
                Icon(imageVector = Icons.Filled.Done, contentDescription = "Share")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ToDoMenuTemplatePreview(){
    ToDoTemplateMenu(
        viewModel = ToDoTemplateViewModel(),
        onClickBackNavigation = {}
    )
}