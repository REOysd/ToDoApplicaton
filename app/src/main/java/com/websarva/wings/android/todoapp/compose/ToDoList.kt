package com.websarva.wings.android.todoapp.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.todoapp.database.toDoEntity

@Composable
fun createToDo(
    itemsList:List<toDoEntity>,
    sortedItemsList:List<toDoEntity>,
    boolean: Boolean,
    onClickDeleteButton: (toDoEntity) -> Unit,
    onClickItemButton:(toDoEntity) -> Unit,
    modifier: Modifier
){
    LazyColumn(modifier = modifier) {
        if(boolean) {
            items(items = itemsList, key = { it.id }) { item ->
                ToDoList(
                    item = item,
                    onClickDeleteButton = onClickDeleteButton,
                    modifier = modifier.clickable { onClickItemButton(item) }
                )
            }
        }else{
                items(items = sortedItemsList, key = { it.id }) { item ->
                    ToDoList(
                        item = item,
                        onClickDeleteButton = onClickDeleteButton,
                        modifier = modifier.clickable { onClickItemButton(item) })
                }
        }
    }
}

@Composable
fun ToDoList(
    item:toDoEntity,
    onClickDeleteButton:(toDoEntity) -> Unit,
    modifier: Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row (modifier = Modifier.padding(start = 20.dp)){
            Text(
                text = "最終更新日：" + item.time,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
        Row(modifier= modifier.fillMaxWidth()) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = modifier.weight(1f))

            IconButton(onClick = {onClickDeleteButton(item)}) {
                Icon(imageVector = Icons.Filled.Close, contentDescription ="削除")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoListPreview(){
    ToDoList(
        item = toDoEntity(id = 1, title = "title", description = "description", time = "time"),
        onClickDeleteButton = {},
        modifier = Modifier
    )
}