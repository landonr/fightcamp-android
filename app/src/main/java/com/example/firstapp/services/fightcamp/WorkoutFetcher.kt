package com.example.firstapp.services.fightcamp

import com.example.firstapp.services.ApiService
import com.example.firstapp.datamodel.WorkoutItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.firstapp.services.resultFromResponse

class WorkoutFetcher {
    suspend fun loadData(page: Int): Result<List<WorkoutItem>> {
        return withContext(Dispatchers.IO) {
            val call = ApiService.fightCampApi().getWorkouts(page)
            return@withContext resultFromResponse(call).map { it.items.orEmpty() }
        }
    }
}