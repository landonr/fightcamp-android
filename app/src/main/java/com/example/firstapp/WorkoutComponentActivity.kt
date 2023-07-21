package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firstapp.ui.theme.FirstAppTheme
import java.text.SimpleDateFormat
import java.util.Date


class WorkoutComponentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val topBarState = rememberSaveable { (mutableStateOf(true)) }

            WorkoutComposableHost(navController = navController, topBarState = topBarState)
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
fun PhotoCard(modifier: Modifier = Modifier, workout: WorkoutItems) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(workout.previewImgUrl)
            .crossfade(true)
            .build(),
        contentDescription = workout.desc,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun WorkoutCard(modifier: Modifier = Modifier, workoutItem: Pair<WorkoutItems, TrainerModel?>) {
    val padding = 16.dp
    Card(
        modifier = Modifier
            .padding(padding)
            .height(110.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row() {
            WorkoutCardInfo(
                modifier
                    .weight(1f)
                    .padding(8.dp), workoutItem)
            Box(modifier = Modifier
                .background(Color.DarkGray)
                .width(148.dp)
                .fillMaxHeight()) {
                PhotoCard(modifier, workoutItem.first)
            }
        }
    }
}

@Composable
private fun WorkoutCardInfo(
    modifier: Modifier = Modifier,
    workoutItem: Pair<WorkoutItems, TrainerModel?>
) {
    fun convertIntToTime(time: Int): String {
        val date = Date(time.toLong() * 1000)
        val format = SimpleDateFormat("MM/dd/yyyy")
        return format.format(date)
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        Text(
            text = workoutItem.first.title ?: "none",
            fontWeight = FontWeight.Bold
        )
        val name =
            (workoutItem.second?.firstName ?: "") + " " + workoutItem.second?.lastName ?: ""
        val nameFontSize = 14.sp
        Row(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Text(
                text = name,
                fontSize = nameFontSize
            )
            workoutItem.first.added?.let {
                Text(
                    text = " ⦁ " + convertIntToTime(it),
                    maxLines = 1,
                    fontSize = nameFontSize
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirstAppTheme {
        val navController = rememberNavController()
        WorkoutComposableColumn(navController = navController)
    }
}