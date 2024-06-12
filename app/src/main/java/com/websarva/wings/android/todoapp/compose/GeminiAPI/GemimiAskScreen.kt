package com.websarva.wings.android.todoapp.compose.GeminiAPI

import android.util.Log
import android.webkit.WebSettings.TextSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.websarva.wings.android.todoapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeminiAskScreen(
    viewModel: GeminiAPIViewModel,
    modifier: Modifier
){
    val UiState = viewModel.UiState.collectAsState()
    val IoScope = CoroutineScope(Dispatchers.IO)
    val focusManager = LocalFocusManager.current


    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row{
                        Text(
                            text = "AskGemini",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.gemini),
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                        },
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    ){content ->
        Box(modifier = modifier.fillMaxSize()){
            TextField(
                value = viewModel.inputText,
                onValueChange = { viewModel.inputText = it },
                shape = RoundedCornerShape(8.dp),
                label = { Text(text = stringResource(id = R.string.labelOnTextField))},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.addMessage(
                            message = viewModel.inputText,
                            participant = false
                        )
                        viewModel.onClickKeyboardDone(
                            scope = IoScope,
                            viewModel = viewModel,
                            string = viewModel.inputText,
                            participant = true
                        )
                        viewModel.inputText = ""
                        focusManager.clearFocus()
                        Log.d("tag",viewModel.UiState.value.toString())
                    }
                ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(content)
                    .padding(start = 8.dp, end = 8.dp)
            )
            CardList(
                modifier = Modifier.padding(),
                UiState = UiState.value
            )
        }
    }
}

@Composable
fun CardList(
    modifier: Modifier,
    UiState:List<data>
){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 150.dp)
    ) {
        items(items = UiState, key = {it.id}){card ->
            CreateCard(text = card.text, participant = card.participant)
        }
    }
}

@Composable
fun CreateCard(text:String,participant:Boolean){
    if(participant){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 70.dp, bottom = 16.dp)
        ){
            Row(
                modifier = Modifier
            ) {
                SelectionContainer {
                    Text(
                        text = text,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(10.dp)
                            .padding(start = 14.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }else{
        Card(
            modifier = Modifier
                .padding(start = 100.dp, top = 16.dp, end = 10.dp, bottom = 16.dp)
            ){
            Row(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                SelectionContainer(
                    modifier = Modifier
                        .background(color = Color.Green.copy(alpha = 0.45f))
                        .fillMaxWidth()
                ) {
                    Text(
                        text = text,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(10.dp)
                            .padding(start = 14.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Right
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GeminiAskScreenPreview(){
    GeminiAskScreen(viewModel = GeminiAPIViewModel(), modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun CreateCardPreview(){
    CreateCard(text = "text", participant = false)
}