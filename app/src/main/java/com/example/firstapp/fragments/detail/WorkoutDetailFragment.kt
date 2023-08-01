package com.example.firstapp.fragments.detail

import WorkoutAndTrainer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firstapp.R
import getSerializableSafe

class WorkoutDetailFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val workoutCardView = WorkoutDetailView(view)
        if (arguments?.containsKey(getString(R.string.workout)) == true) {
            val data = arguments?.getSerializableSafe(
                getString(R.string.workout),
                WorkoutAndTrainer::class.java
            )
            data?.run {
                this.workout.run {
                    workoutCardView.setupWorkoutDetails(this)
                }
                this.trainer?.run {
                    workoutCardView.setupTrainerDetails(this)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.workout_detail_fragment, container, false)
    }
}