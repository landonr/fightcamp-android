package com.example.firstapp.fragments

import WorkoutAndTrainer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.firstapp.R
import dagger.hilt.android.AndroidEntryPoint

interface OnItemClickListener {
    fun onItemClick(data: WorkoutAndTrainer)
}

@AndroidEntryPoint
class WorkoutFragment : Fragment(), OnItemClickListener {
    private val viewModel by viewModels<FragmentItemViewModel>()
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        // Inflate the fragment's XML layout
        var view = layoutInflater.inflate(R.layout.workout_fragment, null, false)
        loadingIndicator = view.findViewById<ProgressBar>(R.id.progressBar)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        setupLoadingIndicatorListener()
        setupRecyclerView(view)
        setupRefreshListener()

        return view
    }

    override fun onItemClick(data: WorkoutAndTrainer) {
        findNavController().navigate(R.id.workoutDetailFragment, bundleOf(getString(R.string.workout) to data))
    }

    private fun setupLoadingIndicatorListener() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                showLoadingIndicator()
            } else {
                hideLoadingIndicator()
            }
        })
    }

    private fun setupRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener {
            // Handle the data refresh here
            viewModel.loadMoreData()
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this.context)
        recyclerview.layoutManager = layoutManager
        val adapter = CustomAdapter(emptyList(), this)
        recyclerview.adapter = adapter
        bindToResultLiveData(adapter)
        setupLoadMoreListener(recyclerview, layoutManager)
    }

    private fun setupLoadMoreListener(
        recyclerview: RecyclerView,
        layoutManager: LinearLayoutManager
    ) {
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (viewModel.isLoading.value == false && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    // Load more data
                    viewModel.loadMoreData()
                }
            }
        })
    }

    private fun bindToResultLiveData(adapter: CustomAdapter) {
        viewModel.result.observe(viewLifecycleOwner, Observer { updatedResult ->
            // Update the adapter's data list with the new data and notify the adapter
            adapter.dataList = updatedResult
            adapter.notifyDataSetChanged()
        })
    }

    private fun hideLoadingIndicator() {
        loadingIndicator.isVisible = false
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showLoadingIndicator() {
        loadingIndicator.isVisible = true
    }
}