package com.pbd.psi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pbd.psi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedpreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedpreferences = getSharedPreferences(LoginActivity.SHARED_PREFS, Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        binding.textEmail.text=sharedpreferences.getString(TOKEN, "default")
        binding.textToken.text=sharedpreferences.getString(EMAIL, "default")
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener{
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()
            val intentLogin = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intentLogin)
            finish()

        }
    }
}