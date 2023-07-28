package com.example.firstapp.compose

import WorkoutAndTrainer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstapp.fragments.IFragmentItemViewModel
import com.example.firstapp.services.WorkoutManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class ComposeItemViewModel @Inject constructor() : ViewModel(), Serializable, IFragmentItemViewModel {
    private lateinit var repo: WorkoutManager
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
        val workoutList = repo.loadData(page)
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
