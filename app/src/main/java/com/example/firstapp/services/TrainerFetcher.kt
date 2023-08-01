package com.example.firstapp.services

import ApiService
import com.example.firstapp.datamodel.TrainerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import resultFromResponse

class TrainerFetcher() {
    suspend fun loadTrainer(trainerId: Int): Result<TrainerModel> {
        return withContext(Dispatchers.IO) {
            val call = ApiService.fightCampApi().getTrainerById(trainerId = trainerId)
            return@withContext resultFromResponse(call)
        }
    }
}