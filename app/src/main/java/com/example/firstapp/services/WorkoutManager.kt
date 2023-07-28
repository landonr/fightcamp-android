package com.example.firstapp.services

import Logger.Companion.debugLog
import WorkoutAndTrainer
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutManager @Inject constructor() {
    private var trainers: List<TrainerModel> = emptyList()

    suspend fun loadData(page: Int): Result<List<WorkoutAndTrainer>> {
        debugLog("WorkoutManager", "loadData page $page")
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
                        trainers.filter { workout.trainerId == it.id }.firstOrNull()
                    )
                }
            )
        }
    }

    private suspend fun loadWorkouts(page: Int): Result<List<WorkoutItem>> {
        debugLog("WorkoutManager" , "loading workouts page $page")
        return WorkoutFetcher().loadData(page)
    }

    private suspend fun loadTrainers(workouts: List<WorkoutItem>): List<TrainerModel> {
        val trainerCopy = trainers.toMutableList()
        debugLog("WorkoutManager" , "loading trainers count=${trainerCopy.size}")
        workouts.map { workout ->
            if (trainerCopy.filter { it.id == workout.trainerId }.isEmpty() ) {
                val index = trainerCopy.size
                val tempTrainer = TrainerModel()
                tempTrainer.id = workout.trainerId
                trainerCopy.add(tempTrainer)
                val trainerResult = loadTrainer(workout.trainerId)
                if (trainerResult.isFailure) {
                    debugLog("WorkoutManager", "loading trainer failed ${workout.trainerId} ${trainerResult.exceptionOrNull()}")
                } else {
                    trainerResult.getOrNull()?.run {
                        trainerCopy.set(index, this)
                    }
                }
            }
        }
        return trainerCopy
    }

    private suspend fun loadTrainer(
        trainerId: Int
    ): Result<TrainerModel> {
        debugLog("WorkoutManager" , "loading trainer $trainerId")
        return TrainerFetcher().loadTrainer(trainerId)
    }
}

