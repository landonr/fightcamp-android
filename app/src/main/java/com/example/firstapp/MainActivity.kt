package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firstapp.ui.theme.FirstAppTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAppTheme {
                val padding = 16.dp
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val viewModel = remember { ItemViewModel() }
                    MyColumn(viewModel)
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        label = { Text("Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )
}

fun GetFilteredList(results: List<Pair<WorkoutItems, TrainerModel?>>, state: MutableState<TextFieldValue>): List<Pair<WorkoutItems, TrainerModel?>> {
    if (state.value.text.isNotEmpty()) {
        return results.filter {
            var searchTitle = state.value.text
                .lowercase().replace(" ", "")
            var valueTitle = it.first.title?: ""
            return@filter valueTitle
                .lowercase().replace(" ", "")
                .contains(searchTitle)
        }
    }
    return results
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore : () -> Unit
){
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}

@Composable
fun MyColumn(viewModel: ItemViewModel = ItemViewModel() ) {
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
                    WorkoutCard(it)
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

@Composable
fun PhotoCard(workout: WorkoutItems, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(workout.previewImgUrl)
            .crossfade(true)
            .build(),
        contentDescription = workout.desc,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun WorkoutCard(workoutItem: Pair<WorkoutItems, TrainerModel?>) {
    val padding = 16.dp
    Card(
        modifier = Modifier
            .padding(padding)
            .height(110.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier
                .background(Color.Red)
                .weight(1f)
                .fillMaxHeight()
                .padding(padding)) {
                Text(
                    text = workoutItem.first.title ?: "none",
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
                val name =
                    (workoutItem.second?.firstName ?: "") + " " + workoutItem.second?.lastName ?: ""
                Text(
                    text = name,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Box(modifier = Modifier
                .background(Color.Yellow)
                .width(148.dp)
                .fillMaxHeight()) {
                PhotoCard(workout = workoutItem.first)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirstAppTheme {
        MyColumn()
    }
}