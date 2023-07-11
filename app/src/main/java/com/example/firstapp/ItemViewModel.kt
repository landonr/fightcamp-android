package com.example.firstapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
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

    fun loadData(): List<TrainerModel> {
        var response = listOf<TrainerModel>()
        try {
            val jsonString = URL("https://android-trial.fightcamp.io/trainers").readText()
            val sType = object : TypeToken<AllTrainerModel>() {}.type
            val model = Gson().fromJson<AllTrainerModel>(jsonString, sType)
            response = model.items?: emptyList()
        } catch (ex: Exception) {
            print(ex)
        }
        return response
    }
}

class WorkoutFetcher() {
    fun loadData(page: Int): List<WorkoutItems> {
        var response = listOf<WorkoutItems>()
        try {
            val jsonString = URL("https://android-trial.fightcamp.io/workouts?offset=$page").readText()
            val sType = object : TypeToken<FightcampModel>() {}.type
            val model = Gson().fromJson<FightcampModel>(jsonString, sType)
            response = model.items?: emptyList()
        } catch (ex: Exception) {
            print(ex)
        }
        return response
    }
}

class ImageFetcher() {
    suspend fun loadData(imageUrl: URL): Result<ByteArray> {
        // Launch a new coroutine in the scope
        return withContext(Dispatchers.IO) {
            try {
                val byteArray = imageUrl.readBytes()
                return@withContext Result.success(byteArray)
            } catch (ex: Exception) {
                print(ex)
                return@withContext Result.failure(ex)
            }
        }
    }
}

class ItemViewModel : ViewModel() {
    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Default + CoroutineName("BackgroundCoroutine"))

    var trainers by mutableStateOf(emptyList<TrainerModel>())
        private set
    var result by mutableStateOf(emptyList<Pair<WorkoutItems, TrainerModel?>>())
        private set

    var page = mutableStateOf(0)
        private set
    var isLoading by mutableStateOf(false)
        private set


    init {
        Log.d("LOADME", "viewmodel init")
        val page = page.value
        isLoading = true
        viewModelScope.launch {
            loadData(page)
        }
    }

    private suspend fun loadData(page: Int) {
        Log.d("LOADME", "loadData page $page")
        loadWorkouts(page).onSuccess { workouts ->
            val trainers = LoadTrainers(workouts)
            setResult(workouts, trainers)
        }

    }

    private fun setResult(
        workouts: List<WorkoutItems>,
        trainers: List<TrainerModel>
    ) {
        Log.d("LOADME", "setting page $page result $workouts $trainers")
        val newResult = workouts.map { workout ->
            Pair<WorkoutItems, TrainerModel?>(workout, trainers.firstOrNull { trainerModel ->
                trainerModel.id == workout.trainerId
            })
        }
        result += newResult
        isLoading = false
    }

    private fun loadWorkouts(page: Int): Result<List<WorkoutItems>> {
        Log.d("LOADME" , "loading workouts page $page")
        return Result.success(WorkoutFetcher().loadData(page))
    }

    private suspend fun LoadTrainers(workouts: List<WorkoutItems>): List<TrainerModel> {
        val trainerCopy = trainers.toMutableList()
        workouts.map {
                val index = trainerCopy.size
                var tempTrainer = TrainerModel()
                tempTrainer.id = it.trainerId
                trainerCopy.add(tempTrainer)
                loadTrainer(it)?.let { trainer ->
                    trainerCopy.set(index, trainer)
                }
        }
        return trainerCopy
    }

    private suspend fun loadTrainer(
        item: WorkoutItems
    ): TrainerModel? {
        item.trainerId?.also { trainerId ->
            if (trainers.filter { it.id == trainerId }.isEmpty()) {
                return TrainerFetcher().loadTrainer(trainerId).getOrNull()
            }
        }
        return null
    }

    fun loadMoreData() {
        if (isLoading) {
            Log.d("LOADME", "NOT loading more page $page")
            return
        }
        isLoading = true
        page.value += 1
        val page = page.value
        viewModelScope.launch {
            Log.d("LOADME", "loading more page $page")
            loadData(page)
        }
    }
}
