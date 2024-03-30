package com.pbd.psi

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pbd.psi.databinding.ActivityMainBinding
import com.pbd.psi.databinding.FragmentSettingsBinding
import com.pbd.psi.services.BackgroundService
import android.content.res.ColorStateList

import android.graphics.Color

class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var settingsBinding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        settingsBinding = FragmentSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

        // bottom navbar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

//        setSupportActionBar(findViewById(R.id.toolbar))
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)  // Hide back button
//        supportActionBar?.setDisplayShowHomeEnabled(false)  // Hide logo
//        supportActionBar?.setDisplayShowTitleEnabled(true)  // Show title only
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        val serviceIntent = Intent(this, BackgroundService::class.java)
        startService(serviceIntent)

        settingsBinding.btnKeluar.setOnClickListener {
            with(sharedpreferences.edit()) {
                clear()
                apply()
            }
            val intentLogin = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intentLogin)
            stopService(serviceIntent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }


}
