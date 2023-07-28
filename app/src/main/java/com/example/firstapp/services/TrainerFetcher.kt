package com.example.firstapp.services

import com.example.firstapp.datamodel.TrainerModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class TrainerFetcher() {
    suspend fun loadTrainer(trainerId: Int): Result<TrainerModel> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString =
                    URL("https://android-trial.fightcamp.io/trainers/$trainerId").readText()
                val sType = object : TypeToken<TrainerModel>() {}.type
                val trainer = Gson().fromJson<TrainerModel>(jsonString, sType)
                return@withContext Result.success(trainer)
            } catch (ex: Exception) {
                print(ex)
                return@withContext Result.failure(ex)
            }
        }
    }
}