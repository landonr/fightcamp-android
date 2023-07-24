package com.example.firstapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.firstapp.fragments.ComponentEmbedFragment
import com.example.firstapp.fragments.WorkoutFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragment1: Fragment
    private lateinit var fragment2: Fragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    // Declare more Fragments for additional tabs

    // Declare more Fragments for additional tabs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.firstapp.R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        layout.setupWithNavController(toolbar, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.workoutDetailFragment) {
                layout.visibility = View.VISIBLE
                bottomNavigationView.visibility = View.GONE
            } else {
                layout.visibility = View.GONE
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}