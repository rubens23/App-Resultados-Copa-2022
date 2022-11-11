package com.example.jogoscopadomundo2022.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.jogoscopadomundo2022.R
import com.example.jogoscopadomundo2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val navHostFragment =
            (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)


        //toolbar config
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)




    }

    override fun onPause() {
        super.onPause()
        Log.d("ciclomainactivity", "to no on pause da main activity")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ciclomainactivity", "to no on stop da main activity")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ciclomainactivity", "to no on destroy da main activity")

    }





}