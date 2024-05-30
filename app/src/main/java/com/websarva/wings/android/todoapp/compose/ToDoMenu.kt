package com.websarva.wings.android.todoapp.compose

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.websarva.wings.android.todoapp.R


@Composable
fun ToDoMenu(
    viewModel: toDoViewModel,
    onClickBackNavigation:() -> Unit,
    ) {
    val focusManager = LocalFocusManager.current

    viewModel.formattedTimer.collectAsState().value.let{formattedTimer ->
        Scaffold(
            topBar = {
                TopAppBar_screen(
                    onClickBackNavigation = onClickBackNavigation,
                    time = formattedTimer,
                    viewModel = viewModel
                )
            },
            modifier = Modifier.pointerInput(Unit){
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
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
                        label = { Text(text = stringResource(id = R.string.toDo_title)) },
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
                        label = { Text(text = stringResource(id = R.string.toDo_description)) },
                        singleLine = false,
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ToDoMenuPreview(){
    ToDoMenu(
        viewModel = toDoViewModel(),
        onClickBackNavigation = {}
    )
}
