package com.example.firstapp.compose.viewModels

import Logger
import WorkoutAndTrainer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.firstapp.fragments.viewModels.IFragmentItemViewModel
import com.example.firstapp.services.fightcamp.WorkoutManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class ComposeItemViewModel @Inject constructor(
    private var workoutManager: WorkoutManager
    ) : ViewModel(), Serializable, IFragmentItemViewModel {
    private val viewModelScope =
        CoroutineScope(Job() + Dispatchers.Default + CoroutineName("BackgroundCoroutineCompose"))
    val result = mutableStateOf<List<WorkoutAndTrainer>>(emptyList())
    val page = mutableStateOf(0)
    val isLoading = mutableStateOf(false)
    init {
        Logger.debugLog("ComposeItemViewModel", "viewmodel init")
        val page = page.value
        isLoading.value = true
        viewModelScope.launch {
            loadData(page)
        }
    }

    private suspend fun loadData(page: Int, clear: Boolean = false) {
        Logger.debugLog("ComposeItemViewModel", "loadData page $page")
        val workoutList = workoutManager.loadData(page)
        workoutList.onSuccess {
            viewModelScope.launch(Dispatchers.Main){
                if (clear) {
                    result.value = it
                } else {
                    result.value = result.value + it
                }
                isLoading.value = false
                Logger.debugLog("ComposeItemViewModel", "data loaded count ${it.size} total ${result.value.size}")
            }
        }
    }

    override fun loadMoreData() {
        if (isLoading.value == true) {
            Logger.debugLog("ComposeItemViewModel", "NOT loading more page ${page.value}")
            return
        }
        isLoading.value = true
        page.value += 1
        val page = page.value
        viewModelScope.launch {
            Logger.debugLog("ComposeItemViewModel", "loading more page $page")
            loadData(page)
        }
    }

    override fun reloadData() {
        page.value = 0
        viewModelScope.launch {
            loadData(0, true)
        }
    }
}