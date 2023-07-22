package com.example.firstapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.firstapp.fragments.ComponentEmbedFragment
import com.example.firstapp.fragments.WorkoutFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragment1: Fragment
    private lateinit var fragment2: Fragment
    // Declare more Fragments for additional tabs

    // Declare more Fragments for additional tabs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.firstapp.R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(com.example.firstapp.R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.example.firstapp.R.id.nav_item_1 -> {
                    replaceFragment(fragment1)
                    true
                }

                com.example.firstapp.R.id.nav_item_2 -> {
                    replaceFragment(fragment2)
                    true
                }

                else -> false
            }
        }
        fragmentManager = supportFragmentManager
        fragment1 = WorkoutFragment()
        fragment2 = ComponentEmbedFragment()
        // Initialize more Fragments for additional tabs

        // Set the default fragment to be displayed
        replaceFragment(fragment1)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(com.example.firstapp.R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}