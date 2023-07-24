package com.example.firstapp.datamodel

import java.text.SimpleDateFormat
import java.util.Date

typealias WorkoutAndTrainer = Pair<WorkoutItems, TrainerModel?>

val TrainerModel.fullTitle: String
    get() = (firstName ?: "") + " " + lastName ?: ""

val WorkoutItems.dateString: String
    get() {
        added?.let { time ->
            val date = Date(time.toLong() * 1000)
            val format = SimpleDateFormat("MM/dd/yyyy")
            return " ⦁ " + format.format(date)
        }
        return ""
    }