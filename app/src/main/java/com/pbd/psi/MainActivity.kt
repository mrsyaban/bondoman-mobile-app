package com.pbd.psi

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pbd.psi.databinding.ActivityMainBinding
import com.pbd.psi.databinding.FragmentSettingsBinding
import com.pbd.psi.services.BackgroundService

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

        val serviceIntent = Intent(this, BackgroundService::class.java)
        startService(serviceIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        val serviceIntent = Intent(this, BackgroundService::class.java)
        stopService(serviceIntent)
    }

}
