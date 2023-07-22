package com.example.firstapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItems
import com.example.firstapp.datamodel.dateString
import com.example.firstapp.datamodel.fullTitle

class CustomAdapter(var dataList: List<Pair<WorkoutItems, TrainerModel?>>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val workout = dataList[position]

        // sets the image to the imageview from our itemHolder class
        workout.first.previewImgUrl?.let {
            Glide.with(holder.imageView)
                .load(it)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(holder.imageView)
        }

        // sets the text to the textview from our itemHolder class
        holder.titleTextView.text = workout.first.title
        holder.trainerTextView.text = workout.second?.fullTitle
        holder.dateTextView.text = workout.first.dateString
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val trainerTextView: TextView = itemView.findViewById(R.id.trainerTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    }
}