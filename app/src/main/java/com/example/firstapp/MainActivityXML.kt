package com.example.firstapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivityXML() : AppCompatActivity() {
    lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_xml)
        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
//        val viewModel = intent?.getSerializableExtra("viewModel") as ItemViewModel
//        // Assign the ViewModel to the activity's property
//        this.viewModel = viewModel
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(emptyList())

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        // Observe changes in the ViewModel's `result` property
        viewModel.resultLiveData.observe(this, Observer { updatedResult ->
            // Update the adapter's data list with the new data and notify the adapter
            adapter.dataList = updatedResult
            adapter.notifyDataSetChanged()
        })
    }

//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        super.onCreateView(name, context, attrs)
//
//        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
//
//        // getting the recyclerview by its id
//        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
//
//        // this creates a vertical layout Manager
//        recyclerview.layoutManager = LinearLayoutManager(this)
//
//        // This will pass the ArrayList to our Adapter
//        val adapter = CustomAdapter(viewModel.result)
//
//        // Setting the Adapter with the recyclerview
//        recyclerview.adapter = adapter
//
//        // Return null to use the default implementation for this method
//        return null
//    }
}