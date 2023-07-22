package com.example.firstapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.compose.rememberNavController
import com.example.firstapp.compose.WorkoutComposableHost

class ComponentEmbedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_component_embed, container, false).apply {
            findViewById<ComposeView>(R.id.compose_view).setContent {
                val navController = rememberNavController()
                val topBarState = rememberSaveable { (mutableStateOf(true)) }
                WorkoutComposableHost(navController = navController, topBarState = topBarState)
            }
        }
    }
}