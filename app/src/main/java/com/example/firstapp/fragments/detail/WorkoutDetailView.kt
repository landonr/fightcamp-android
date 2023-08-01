package com.example.firstapp.fragments.detail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.firstapp.R
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItem
import fullTitle

class WorkoutDetailView {
    constructor(itemView: View) {
        this.imageView = itemView.findViewById(R.id.detailImageViewWorkoutPreview)
        this.titleTextView = itemView.findViewById(R.id.detailTextViewWorkoutTitle)
        this.descriptionTextView = itemView.findViewById(R.id.detailTextViewWorkoutDescription)
        this.trainerTextView = itemView.findViewById(R.id.detailTextViewTrainerName)
        this.trainerImageView = itemView.findViewById(R.id.detailImageViewTrainer)
    }

    val imageView: ImageView
    val titleTextView: TextView
    val descriptionTextView: TextView
    val trainerTextView: TextView
    val trainerImageView: ImageView

    fun setupTrainerDetails(
        trainer: TrainerModel
    ) {
        trainerTextView.text = trainer.fullTitle
        trainer.photoUrl?.run {
            Glide.with(trainerImageView)
                .load(this)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(trainerImageView)
        }
    }

    fun setupWorkoutDetails(
        workout: WorkoutItem
    ) {
        titleTextView.text = workout.title
        descriptionTextView.text = workout.desc
        workout.previewImgUrl?.run {
            Glide.with(imageView)
                .load(this)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(imageView)
        }
    }
}