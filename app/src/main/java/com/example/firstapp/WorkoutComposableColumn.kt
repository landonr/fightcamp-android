package com.example.firstapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun WorkoutComposableColumn(viewModel: ItemViewModel = ItemViewModel(), navController: NavHostController, modifier: Modifier = Modifier) {
    val padding = 16.dp
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val listState = rememberLazyListState()
    if(viewModel.result.isEmpty()) {
        LoadingScreen()
    } else {
        Column() {
            SearchBar(textState)
            LazyColumn(state = listState) {
                items(GetFilteredList(viewModel.result, textState)) {
                    WorkoutCard(modifier = Modifier.clickable {
                        navController.navigate("detail/${it.first.id}")
                    }, workoutItem = it)
                }
                if (viewModel.isLoading) { // Assuming you have a flag to indicate loading state
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                            Text(text = viewModel.page.value.toString())
                        }
                    }
                }
            }
            listState.OnBottomReached {
                if (textState.value.text.isEmpty()) {
                    viewModel.loadMoreData()
                }
            }
        }
    }
}