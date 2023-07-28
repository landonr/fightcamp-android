package com.example.firstapp.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.compose.rememberNavController
import com.example.firstapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComponentEmbedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // https://stackoverflow.com/questions/64341415/dagger-hilt-android-internal-managers-viewcomponentmanagerfragmentcontextwrappe
        val baseInflater = LayoutInflater.from(requireActivity()) // NOT context
        return baseInflater.inflate(R.layout.fragment_component_embed, container, false).apply {
            findViewById<ComposeView>(R.id.compose_view).setContent {
                val navController = rememberNavController()
                val topBarState = rememberSaveable { (mutableStateOf(true)) }
                WorkoutComposableHost(navController = navController, topBarState = topBarState)
            }
        }
    }
}