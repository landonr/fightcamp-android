package com.example.firstapp.fragments

import Logger.Companion.debugLog
import WorkoutAndTrainer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstapp.services.WorkoutManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

interface IFragmentItemViewModel {
    val result: MutableLiveData<List<WorkoutAndTrainer>>
    val page: MutableState<Int>
    val isLoading: MutableLiveData<Boolean>
    fun loadMoreData()
    fun reloadData()
}

@HiltViewModel
class FragmentItemViewModel @Inject constructor() : ViewModel(), Serializable, IFragmentItemViewModel {
    @Inject lateinit var workoutManager: WorkoutManager
    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Default + CoroutineName("BackgroundCoroutine"))
    override val result = MutableLiveData<List<WorkoutAndTrainer>>(emptyList())
    override val page = mutableStateOf(0)
    override val isLoading = MutableLiveData<Boolean>(false)
    init {
        debugLog("FragmentItemViewModel", "viewmodel init")
        viewModelScope.launch {
            loadData()
        }
    }

    override fun reloadData() {
        if (isLoading.value == true) {
            debugLog("FragmentItemViewModel", "NOT reloading")
            return
        }
        isLoading.postValue(true)
        viewModelScope.launch {
            page.value = 0
            loadData()
        }

    }

    private suspend fun loadData() {
        debugLog("FragmentItemViewModel", "loadData page $page")
        isLoading.postValue(true)
        val workoutList = workoutManager.loadData(page.value)
        workoutList.onSuccess {
            isLoading.postValue(false)
            result.postValue(it)
        }
    }

    override fun loadMoreData() {
        if (isLoading.value == true) {
            debugLog("FragmentItemViewModel", "NOT loading more page $page")
            return
        }
        isLoading.postValue(true)
        page.value += 1
        viewModelScope.launch {
            debugLog("FragmentItemViewModel", "loading more page $page")
            loadData()
        }
    }
}

