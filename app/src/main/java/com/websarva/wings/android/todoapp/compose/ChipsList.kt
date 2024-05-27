package com.websarva.wings.android.todoapp.compose

import android.util.Log
import android.view.GestureDetector
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Chip
import com.websarva.wings.android.todoapp.database.createTemplate
import kotlinx.coroutines.launch

@Composable
fun ChipsList(
    TemplateList:List<createTemplate>,
    onClickDeleteButtonOnTemplate: (createTemplate) -> Unit
){
    var selectedItemId by remember { mutableStateOf<Int?>(null) }

    LazyColumn(modifier = Modifier.padding(start = 10.dp, end = 20.dp)) {
        items(items = TemplateList, key = {it.id}){TemplateItems->
            TemplateCard(
                TemplateItems = TemplateItems,
                onClickDeleteButtonOnTemplate = onClickDeleteButtonOnTemplate,
                changeCheck = selectedItemId == TemplateItems.id,
                onClick = {
                    if (selectedItemId == TemplateItems.id){
                        selectedItemId = null
                    }else{
                        selectedItemId = TemplateItems.id
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TemplateCard(
    TemplateItems:createTemplate,
    onClickDeleteButtonOnTemplate:(createTemplate) -> Unit,
    changeCheck:Boolean,
    onClick:() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 150.dp, bottom = 10.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .background(
                    if (changeCheck) Color(0xFFB2EBF2).copy(alpha = 0.5f) else Color.LightGray
                )
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        onClick()
                        Log.d("tab", "click")
                    },
                    onLongClick = { Log.d("tab", "onLongClick") }
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if(changeCheck){
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "選択",
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
            Text(
                text = TemplateItems.TemplateTitle,
                modifier = Modifier.padding(start = 15.dp),
                fontStyle = FontStyle(70),
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){ IconButton(
                onClick = {onClickDeleteButtonOnTemplate(TemplateItems)},
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "削除",
                )
            }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun ModalBottomSheetSample(
    TemplateList: List<createTemplate>,
    TemplateViewModel:ToDoTemplateViewModel,
    onClickAddButtonToTemplate:() -> Unit,
    modifier: Modifier = Modifier
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    // App content
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(top = 10.dp, start = 5.dp)
    ) {
        LargeFloatingActionButton(
            onClick = { openBottomSheet = !openBottomSheet },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(40.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "AddTemplate")
        }
    }

    // Sheet content
    if (openBottomSheet) {

        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                    // you must additionally handle intended state cleanup, if any.
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                openBottomSheet = false
                            }
                        }
                    }
                ) {
                    Text("Hide Bottom Sheet")
                }

                Button(onClick = {
                    onClickAddButtonToTemplate()
                    TemplateViewModel.title = ""
                    TemplateViewModel.description = ""
                }) {
                    Text(text = "createTemplate")
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))
            ChipsList(
                TemplateList = TemplateList,
                onClickDeleteButtonOnTemplate = {TemplateViewModel.deleteToDoTemplate(it)}
            )
            
            Text(text = "string")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FiltersChipPreview(){
    TemplateCard(
        TemplateItems = createTemplate(
            id = 1,
            TemplateTitle = "title",
            TemplateText = "text"),
        onClickDeleteButtonOnTemplate = {},
        changeCheck = false,
        onClick = {}
    )
}

