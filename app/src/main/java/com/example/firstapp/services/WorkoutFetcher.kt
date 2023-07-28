package com.example.firstapp.services

import com.example.firstapp.datamodel.WorkoutItem
import com.example.firstapp.datamodel.WorkoutItemList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class WorkoutFetcher() {
    suspend fun loadData(page: Int): Result<List<WorkoutItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = URL("https://android-trial.fightcamp.io/workouts?offset=$page").readText()
                val sType = object : TypeToken<WorkoutItemList>() {}.type
                val model = Gson().fromJson<WorkoutItemList>(jsonString, sType)
                val result = Result.success(model.items ?: emptyList())
                return@withContext result
            } catch (ex: Exception) {
                print(ex)
                return@withContext Result.failure(ex)
            }
        }
    }
}