package com.example.firstapp.compose

import WorkoutAndTrainer
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
import com.example.firstapp.ui.theme.FirstAppTheme

@Composable
fun DetailActivity(navController: NavHostController, detailedInfo: WorkoutAndTrainer) {
    FirstAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(detailedInfo.workout.previewImgUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = detailedInfo.workout.desc,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = detailedInfo.workout.desc ?: "",
                    modifier = Modifier.padding(16.dp)
                )
                detailedInfo.trainer?.run {
                    trainerCard(this, modifier = Modifier.padding(16.dp))
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
        Row {
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