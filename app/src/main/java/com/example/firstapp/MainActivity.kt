package com.example.firstapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupBottonNavigationView(navController, layout)?.run {
            setupDestinationChangedListener(navController, layout, this)
        }
    }

    private fun setupBottonNavigationView(
        navController: NavController,
        layout: CollapsingToolbarLayout
    ): BottomNavigationView? {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        layout.setupWithNavController(toolbar, navController, appBarConfiguration)
        return bottomNavigationView
    }

    private fun setupDestinationChangedListener(
        navController: NavController,
        layout: CollapsingToolbarLayout,
        bottomNavigationView: BottomNavigationView
    ) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val layoutConfiguration = currentLayoutConfiguration(destination.id)
            layout.visibility = layoutConfiguration.layoutVisible
            bottomNavigationView.visibility = layoutConfiguration.bottonNavigationViewVisible
        }
    }

    class LayoutConfiguration(val layoutVisible: Int, val bottonNavigationViewVisible: Int)

    fun currentLayoutConfiguration(destination: Int): LayoutConfiguration {
        return when (destination) {
            R.id.workoutDetailFragment -> LayoutConfiguration(View.VISIBLE, View.GONE)
            R.id.workoutFragment -> LayoutConfiguration(View.GONE, View.VISIBLE)
            R.id.componentEmbedFragment -> LayoutConfiguration(View.GONE, View.VISIBLE)
            else -> LayoutConfiguration(View.VISIBLE, View.GONE)
        }
    }
}
