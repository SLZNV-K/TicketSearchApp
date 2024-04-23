package com.github.slznvk.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()
        binding.apply {
            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.setOnItemSelectedListener { itemMenu ->
                when (itemMenu.itemId) {
                    R.id.airTickets -> {
                        navController.navigate(R.id.mainFragment)
                    }

                    else -> {
                        navController.navigate(R.id.plugFragment)
                    }
                }
                true
            }
        }
    }
}