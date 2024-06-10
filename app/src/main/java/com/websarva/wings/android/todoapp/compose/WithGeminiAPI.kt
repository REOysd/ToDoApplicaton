package com.websarva.wings.android.todoapp.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WithGeminiAPI(
    viewModel: toDoViewModel,
    onClickBackNavigation:() -> Unit,
){
    val state = rememberPagerState(
        pageCount = {2},
        initialPage = 0
    )
    val IconsList = listOf(Icons.Default.Edit,Icons.Default.Star)
    val mainScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(70.dp)) {
            TabRow(
                selectedTabIndex = state.currentPage,
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
            ) {
                IconsList.forEachIndexed { index, imageVector ->
                    Tab(
                        selected = index == state.currentPage,
                        onClick = {
                            mainScope.launch { 
                                state.animateScrollToPage(index)
                            }
                        },
                        modifier = Modifier.height(60.dp)
                    ) {
                        Icon(imageVector = imageVector, contentDescription = "アイコン")
                    }
                }
            }
        }
        }
    ) {context ->
        Text(text = "", modifier = Modifier.padding(context))
        HorizontalPager(state = state) {
            when(it){
                0 -> ToDoMenu(
                    viewModel = viewModel,
                    onClickBackNavigation = onClickBackNavigation,
                    modifier = Modifier.padding(context))
                1 -> textScreen()
            }
        }

    }

}

@Composable
fun textScreen(){
    Text(text = "te")
}

@Preview(showBackground = true)
@Composable
fun WithGeminiAPIPreview(){
    WithGeminiAPI(
        viewModel = toDoViewModel(),
        onClickBackNavigation = {}
    )
}