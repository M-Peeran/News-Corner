package com.peeranm.newscorner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peeranm.newscorner.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
    get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.bindBottomNavBar()
        binding.toggleBottomNavBar()
    }

    private fun ActivityMainBinding.bindBottomNavBar() {
        bottomNavigationView.setupWithNavController(navHost.findNavController())
    }

    private fun ActivityMainBinding.toggleBottomNavBar() {
        navHost.findNavController().addOnDestinationChangedListener { navController, destination, bundle ->
            when (destination.id) {
                R.id.articleDetailsFragment -> bottomNavigationView.visibility = View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}