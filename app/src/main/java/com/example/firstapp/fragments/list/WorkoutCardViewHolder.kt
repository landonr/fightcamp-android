package com.example.firstapp.fragments.list

import WorkoutAndTrainer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.firstapp.R
import dateString
import fullTitle

class WorkoutCardViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageview)
    val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    val trainerTextView: TextView = itemView.findViewById(R.id.trainerTextView)
    val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)

    fun setData(
        workout: WorkoutAndTrainer,
        itemClickListener: OnItemClickListener
    ) {
        workout.workout.previewImgUrl?.run {
            Glide.with(imageView)
                .load(this)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(imageView)
        }

        titleTextView.text = workout.workout.title
        trainerTextView.text = workout.trainer?.fullTitle
        dateTextView.text = workout.workout.dateString
        itemView.setOnClickListener {
            itemClickListener.onItemClick(workout)
        }
    }
}