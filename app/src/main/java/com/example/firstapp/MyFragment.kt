package com.example.firstapp

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyFragment : Fragment() {
    lateinit var viewModel: ItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        // Inflate the fragment's XML layout
        var view = layoutInflater.inflate(R.layout.fragment_xml, null, false)

        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerview.layoutManager = LinearLayoutManager(this.context)
        val adapter = CustomAdapter(emptyList())

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        // Observe changes in the ViewModel's `result` property
        viewModel.resultLiveData.observe(viewLifecycleOwner, Observer { updatedResult ->
            // Update the adapter's data list with the new data and notify the adapter
            adapter.dataList = updatedResult
            adapter.notifyDataSetChanged()
        })
        return view
    }

//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        return super.onCreateView(name, context, attrs)
//        // Inflate the fragment's XML layout
//        var view = layoutInflater.inflate(R.layout.fragment_xml, null, false)
//
//        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
//        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerView)
//
//        recyclerview.layoutManager = LinearLayoutManager(this)
//        val adapter = CustomAdapter(emptyList())
//
//        // Setting the Adapter with the recyclerview
//        recyclerview.adapter = adapter
//
//        // Observe changes in the ViewModel's `result` property
//        viewModel.resultLiveData.observe(this, Observer { updatedResult ->
//            // Update the adapter's data list with the new data and notify the adapter
//            adapter.dataList = updatedResult
//            adapter.notifyDataSetChanged()
//        })
//        return view
//    }

    // Other fragment methods and logic can go here
}