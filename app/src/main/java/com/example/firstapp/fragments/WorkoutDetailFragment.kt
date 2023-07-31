package com.example.firstapp.fragments

import WorkoutAndTrainer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.firstapp.R
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItem
import fullTitle
import getSerializableSafe

class WorkoutDetailFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewHolder = ViewHolder(view)
        if(arguments?.containsKey(getString(R.string.workout)) == true) {
            val data = arguments?.getSerializableSafe(getString(R.string.workout), WorkoutAndTrainer::class.java)
            data?.run {
                this.workout?.run {
                    setupWorkoutDetails(viewHolder, this)
                }
                this.trainer?.run {
                    setupTrainerDetails(viewHolder, this)
                }
            }
        }
    }

    private fun setupTrainerDetails(
        viewHolder: ViewHolder,
        trainer: TrainerModel
    ) {
        viewHolder.trainerTextView.text = trainer.fullTitle
        trainer.photoUrl?.run {
            Glide.with(viewHolder.trainerImageView)
                .load(this)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(viewHolder.trainerImageView)
        }
    }

    private fun setupWorkoutDetails(
        viewHolder: ViewHolder,
        workout: WorkoutItem
    ) {
        viewHolder.titleTextView.text = workout.title
        viewHolder.descriptionTextView.text = workout.desc
        workout.previewImgUrl?.run {
            Glide.with(viewHolder.imageView)
                .load(this)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(viewHolder.imageView)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.workout_detail_fragment, container, false)
    }

    class ViewHolder(itemView: View) {
        val imageView: ImageView = itemView.findViewById(R.id.detailImageViewWorkoutPreview)
        val titleTextView: TextView = itemView.findViewById(R.id.detailTextViewWorkoutTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.detailTextViewWorkoutDescription)
        val trainerTextView: TextView = itemView.findViewById(R.id.detailTextViewTrainerName)
        val trainerImageView: ImageView = itemView.findViewById(R.id.detailImageViewTrainer)
    }
}