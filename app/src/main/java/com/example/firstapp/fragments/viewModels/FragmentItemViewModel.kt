package com.example.firstapp.fragments.viewModels

import Logger.Companion.debugLog
import WorkoutAndTrainer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstapp.services.fightcamp.WorkoutManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

interface IFragmentItemViewModel {
    fun loadMoreData()
    fun reloadData()
}

@HiltViewModel
class FragmentItemViewModel @Inject constructor() : ViewModel(), Serializable,
    IFragmentItemViewModel {
    @Inject lateinit var workoutManager: WorkoutManager
    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Default + CoroutineName("BackgroundCoroutine"))
    val result = MutableLiveData<List<WorkoutAndTrainer>>(emptyList())
    val page = mutableStateOf(0)
    val isLoading = MutableLiveData<Boolean>(false)
    init {
        debugLog("FragmentItemViewModel", "viewmodel init")
        viewModelScope.launch {
            reloadData()
        }
    }

    override fun reloadData() {
        if (isLoading.value == true) {
            debugLog("FragmentItemViewModel", "NOT reloading")
            return
        }
        isLoading.postValue(true)
        viewModelScope.launch {
            val workoutResult = workoutManager.loadData(page.value)
            if (workoutResult.isFailure) {
                debugLog("FragmentItemViewModel", "${workoutResult.exceptionOrNull()}")
                return@launch
            }
            workoutResult.onSuccess {
                result.postValue(it)
                isLoading.postValue(false)
            }
        }
    }

    override fun loadMoreData() {
        if (isLoading.value == true) {
            debugLog("FragmentItemViewModel", "NOT loading more page ${page.value}")
            return
        }
        isLoading.postValue(true)
        page.value += 1
        viewModelScope.launch {
            debugLog("FragmentItemViewModel", "loading more page ${page.value}")
            val workoutResult = workoutManager.loadData(page.value)
            if (workoutResult.isFailure) {
                debugLog("FragmentItemViewModel", "${workoutResult.exceptionOrNull()}")
                return@launch
            }
            workoutResult.onSuccess {
                val resultCopy = (result.value?: emptyList()).toMutableList()
                resultCopy.addAll(it)
                result.postValue(resultCopy)
                isLoading.postValue(false)
            }
        }
    }
}

