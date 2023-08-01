package com.example.firstapp.compose

import WorkoutAndTrainer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firstapp.R
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItem
import com.example.firstapp.ui.theme.FirstAppTheme
import dateString
import fullTitle
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun PhotoCard(modifier: Modifier = Modifier, workout: WorkoutItem) {
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
    workoutItem: WorkoutAndTrainer
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
            text = workoutItem.workout.title ?: "none",
            fontWeight = FontWeight.Bold
        )
        val name = workoutItem.trainer?.fullTitle ?: ""
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
                text = workoutItem.workout.dateString,
                maxLines = 1,
                fontSize = nameFontSize
            )
        }
    }
}

@Composable
fun WorkoutCard(modifier: Modifier = Modifier, workoutItem: WorkoutAndTrainer) {
    val padding = dimensionResource(R.dimen.card_padding)
    Card(
        modifier = Modifier
            .padding(padding)
            .height(dimensionResource(R.dimen.card_height)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.cardViewBackground))
    ) {
        Row {
            WorkoutCardInfo(
                modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.card_padding)), workoutItem
            )
            Box(
                modifier = Modifier
                    .background(colorResource(R.color.brand_gray6))
                    .width(dimensionResource(R.dimen.image_width))
                    .fillMaxHeight()
            ) {
                PhotoCard(modifier, workoutItem.workout)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutCardPreview() {
    FirstAppTheme {
        WorkoutCard(
            Modifier,
            WorkoutAndTrainer(
                WorkoutItem(0, "Text", "Test", "text", 0, 0, "", "", 0),
                TrainerModel(0, "", "", "")
            )
        )
    }
}