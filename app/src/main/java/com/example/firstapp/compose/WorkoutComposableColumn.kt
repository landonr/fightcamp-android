package com.example.firstapp.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.firstapp.compose.viewModels.ComposeItemViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutComposableColumn(
    viewModel: ComposeItemViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val padding = 16.dp
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val listState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isLoading.value,
        onRefresh = viewModel::reloadData
    )
    if (viewModel.isLoading.value && viewModel.result.value.isEmpty()) {
        LoadingScreen()
    } else {
        Box(Modifier.pullRefresh(pullRefreshState)) {
            Column {
                SearchBar(textState)
                LazyColumn(state = listState) {
                    items(GetFilteredList(viewModel.result.value, textState)) {
                        WorkoutCard(modifier = Modifier.clickable {
                            navController.navigate("detail/${it.workout.id}")
                        }, workoutItem = it)
                    }
                    if (viewModel.isLoading.value) {
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
            }
            listState.OnBottomReached {
                if (textState.value.text.isEmpty()) {
                    viewModel.loadMoreData()
                }
            }
            PullRefreshIndicator(
                refreshing = viewModel.isLoading.value,
                state = pullRefreshState,
                modifier = Modifier.align(alignment = Alignment.TopCenter)
            )
        }
    }
}