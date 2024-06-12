package com.websarva.wings.android.todoapp.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.todoapp.R
import com.websarva.wings.android.todoapp.compose.GeminiAPI.GeminiAPIViewModel
import com.websarva.wings.android.todoapp.compose.GeminiAPI.GeminiAskScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoMenuWithGeminiAPI(
    viewModel: toDoViewModel,
    geminiViewModel:GeminiAPIViewModel,
    onClickBackNavigation:() -> Unit,
){
    val state = rememberPagerState(
        pageCount = {2},
        initialPage = 0
    )
    val IconsList = listOf(1,2)
    val mainScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(110.dp)
                    .fillMaxWidth()
            )
            {
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
                        if (imageVector == 1){
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "アイコン",
                                modifier = Modifier.size(40.dp)
                            )
                        }else{
                            Image(
                                painter = painterResource(id = R.drawable.google_gemini_icon),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
        }
    ) {context ->
        HorizontalPager(state = state) {
            when(it){
                0 -> ToDoMenu(
                    viewModel = viewModel,
                    onClickBackNavigation = onClickBackNavigation,
                )
                1 -> GeminiAskScreen(
                    viewModel = geminiViewModel,
                    modifier = Modifier.padding(context)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoTemplateWithGeminiAPI(
    templateViewModel: ToDoTemplateViewModel,
    geminiViewModel:GeminiAPIViewModel,
    onClickBackNavigation:() -> Unit,
){
    val state = rememberPagerState(
        pageCount = {2},
        initialPage = 0
    )
    val IconsList = listOf(1,2)
    val mainScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(110.dp)) {
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
                            if (imageVector == 1){
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "アイコン",
                                    modifier = Modifier.size(40.dp)
                                )
                            }else{
                                Image(
                                    painter = painterResource(id = R.drawable.google_gemini_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {context ->
        HorizontalPager(state = state) {
            when(it){
                0 -> ToDoTemplateMenu(
                    viewModel = templateViewModel,
                    onClickBackNavigation = onClickBackNavigation
                )
                1 -> GeminiAskScreen(
                    viewModel = geminiViewModel,
                    modifier = Modifier.padding(context)
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun WithGeminiAPIPreview(){
    ToDoMenuWithGeminiAPI(
        viewModel = toDoViewModel(),
        onClickBackNavigation = {},
        geminiViewModel = GeminiAPIViewModel()
    )
}