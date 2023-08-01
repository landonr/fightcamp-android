package com.example.firstapp.services

import com.example.firstapp.services.fightcamp.ApiConstants
import com.example.firstapp.services.fightcamp.FightCampApiService
import retrofit2.Response
import retrofit2.Retrofit

object ApiService {
    fun fightCampApi(): FightCampApiService = Retrofit.Builder()
        .baseUrl(ApiConstants.fightCampBaseURL)
        .addConverterFactory(ApiWorker.gsonConverter)
        .client(ApiWorker.client)
        .build()
        .create(FightCampApiService::class.java)
}

fun <T> resultFromResponse(call: Response<T>): Result<T> {
    if (call.isSuccessful) {
        call.body()?.run {
            return Result.success(this)
        }
        return Result.failure(Throwable("Value missing"))
    }
    call.errorBody()?.string()?.run {
        return Result.failure(Throwable(this))
    }
    return Result.failure(Throwable("Value missing"))
}