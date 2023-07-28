package com.example.firstapp.services

import WorkoutAndTrainer
import android.util.Log
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItem
import javax.inject.Inject

class WorkoutManager @Inject constructor() {
    private var trainers: List<TrainerModel> = emptyList()

    suspend fun loadData(page: Int): Result<List<WorkoutAndTrainer>> {
        Log.d("LOADME", "loadData page $page")
        val workouts = loadWorkouts(page)
        val exception = workouts.exceptionOrNull()
        val workoutResult = workouts.getOrDefault(emptyList())
        if (exception != null || workouts.isFailure) {
            return Result.failure(exception?: Throwable())
        } else {
            trainers = loadTrainers(workoutResult)
            return Result.success(
                workoutResult.map { workout ->
                    WorkoutAndTrainer(
                        workout,
                        trainers.filter { workout.id == it.id }.firstOrNull()
                    )
                }
            )
        }
    }

    private fun loadWorkouts(page: Int): Result<List<WorkoutItem>> {
        Log.d("LOADME" , "loading workouts page $page")
        return WorkoutFetcher().loadData(page)
    }

    private suspend fun loadTrainers(workouts: List<WorkoutItem>): List<TrainerModel> {
        val trainerCopy = trainers.toMutableList()
        workouts.map { workout ->
            if (trainers.filter { it.id == workout.trainerId }.isEmpty() ) {
                val index = trainerCopy.size
                val tempTrainer = TrainerModel()
                tempTrainer.id = workout.trainerId
                trainerCopy.add(tempTrainer)
                val newTrainer = loadTrainer(workout.trainerId)
                newTrainer.getOrNull()?.run {
                    trainerCopy.set(index, this)
                }
            }
        }
        return trainerCopy
    }

    private suspend fun loadTrainer(
        trainerId: Int
    ): Result<TrainerModel> {
        return TrainerFetcher().loadTrainer(trainerId)
    }
}

