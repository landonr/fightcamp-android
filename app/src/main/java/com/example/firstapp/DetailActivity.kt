package com.example.firstapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItems
import com.example.firstapp.ui.theme.FirstAppTheme

@Composable
fun DetailActivity(navController: NavHostController, detailedInfo: Pair<WorkoutItems, TrainerModel?>) {
    FirstAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column() {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(detailedInfo.first.previewImgUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = detailedInfo.first.desc,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = detailedInfo.first.desc?: "",
                    modifier = Modifier.padding(16.dp)
                )
                detailedInfo.second?.let { trainer ->
                    trainerCard(trainer, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
private fun trainerCard(trainer: TrainerModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row() {
            val trainerName =
                trainer.firstName + " " + trainer.lastName
            Text(
                text = trainerName,
                modifier = Modifier.padding(16.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(trainer.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = trainerName,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    FirstAppTheme {
        Greeting("Android")
    }
}