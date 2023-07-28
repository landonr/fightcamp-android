package com.example.firstapp.fragments

import WorkoutAndTrainer
import android.util.Log
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
}

@HiltViewModel
class FragmentItemViewModel @Inject constructor() : ViewModel(), Serializable, IFragmentItemViewModel {
    @Inject lateinit var workoutManager: WorkoutManager
    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Default + CoroutineName("BackgroundCoroutine"))
    override val result = MutableLiveData<List<WorkoutAndTrainer>>(emptyList())
    override val page = mutableStateOf(0)
    override val isLoading = MutableLiveData<Boolean>(false)
    init {
        Log.d("LOADME", "viewmodel init")
        val page = page.value
        viewModelScope.launch {
            loadData(page)
        }
    }

    private suspend fun loadData(page: Int) {
        Log.d("LOADME", "loadData page $page")
        isLoading.postValue(true)
        val workoutList = workoutManager.loadData(page)
        workoutList.onSuccess {
            isLoading.postValue(true)
            result.postValue(it)
        }
    }

    override fun loadMoreData() {
        if (isLoading.value == true) {
            Log.d("LOADME", "NOT loading more page $page")
            return
        }
        isLoading.postValue(true)
        page.value += 1
        val page = page.value
        viewModelScope.launch {
            Log.d("LOADME", "loading more page $page")
            loadData(page)
        }
    }
}

