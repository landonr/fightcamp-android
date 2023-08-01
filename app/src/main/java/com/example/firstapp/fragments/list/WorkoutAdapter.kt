package com.example.firstapp.fragments.list

import WorkoutAndTrainer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R

class WorkoutAdapter(
    var dataList: List<WorkoutAndTrainer>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<WorkoutCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return WorkoutCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutCardViewHolder, position: Int) {
        holder.setData(dataList[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}