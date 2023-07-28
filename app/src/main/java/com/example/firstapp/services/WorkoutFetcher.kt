package com.example.firstapp.services

import com.example.firstapp.datamodel.WorkoutItem
import com.example.firstapp.datamodel.WorkoutItemList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

class WorkoutFetcher() {
    fun loadData(page: Int): Result<List<WorkoutItem>> {
        var response = listOf<WorkoutItem>()
        try {
            val jsonString = URL("https://android-trial.fightcamp.io/workouts?offset=$page").readText()
            val sType = object : TypeToken<WorkoutItemList>() {}.type
            val model = Gson().fromJson<WorkoutItemList>(jsonString, sType)
            response = model.items?: emptyList()
        } catch (ex: Exception) {
            print(ex)
            return Result.failure(ex)
        }
        return Result.success(response)
    }
}