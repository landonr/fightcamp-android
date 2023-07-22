package com.example.firstapp.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItems
import com.example.firstapp.datamodel.dateString
import com.example.firstapp.datamodel.fullTitle
import java.text.SimpleDateFormat
import java.util.Date

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
        val name = workoutItem.second?.fullTitle?: ""
        val nameFontSize = 14.sp
        Row(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Text(
                text = name,
                fontSize = nameFontSize
            )
            Text(
                text = workoutItem.first.dateString,
                maxLines = 1,
                fontSize = nameFontSize
            )
        }
    }
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
                    .padding(8.dp), workoutItem
            )
            Box(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .width(148.dp)
                    .fillMaxHeight()
            ) {
                PhotoCard(modifier, workoutItem.first)
            }
        }
    }
}