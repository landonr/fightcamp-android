package com.example.firstapp.services.fightcamp

import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItemList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FightCampApiService {
    @GET("workouts")
    suspend fun getWorkouts(@Query("offset") page: Int): Response<WorkoutItemList>

    @GET("trainers/{id}")
    suspend fun getTrainerById(@Path("id") trainerId: Int): Response<TrainerModel>
}