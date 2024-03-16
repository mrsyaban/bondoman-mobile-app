package com.pbd.psi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pbd.psi.databinding.ActivityMainBinding
import com.pbd.psi.services.BackgroundService

class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        binding.textEmail.text = sharedpreferences.getString(TOKEN, "default")
        binding.textToken.text = sharedpreferences.getString(EMAIL, "default")

        val serviceIntent = Intent(this, BackgroundService::class.java)
        startService(serviceIntent)

        binding.btnLogout.setOnClickListener {
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
}
