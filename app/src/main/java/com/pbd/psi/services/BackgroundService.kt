package com.pbd.psi.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.pbd.psi.LoginActivity
import com.pbd.psi.api.ApiConfig
import com.pbd.psi.models.AuthRes
import kotlinx.coroutines.*

class BackgroundService : Service() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var sharedpreferences: SharedPreferences
    private var isServiceRunning = false
    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("BackgroundService", "onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isServiceRunning) {
            isServiceRunning = true
            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val token = sharedpreferences.getString(TOKEN, "default").toString()
            Log.d("BackgroundService", token)
            startBackgroundTask(token)
        }
        return START_STICKY
    }

    private fun startBackgroundTask(token: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            while (isServiceRunning) {
                try {
                    delay(10000)
                    Log.d("BackgroundService", "Running")
                    try {
                        val response = ApiConfig.api.auth("Bearer $token")
                        if (response.isSuccessful) {
                            Log.d("BackgroundService", "onResponse: ${response.body()}")
                        } else {
                            withContext(Dispatchers.Main) {
                                val editor = sharedpreferences.edit()
                                editor.clear()
                                editor.apply()
                                Toast.makeText(this@BackgroundService, "Session expired", Toast.LENGTH_SHORT).show()
                                val loginIntent = Intent(this@BackgroundService, LoginActivity::class.java)
                                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(loginIntent)
                                stopSelf()
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("BackgroundService", "Error: ${e.message}")
                    }
                    Log.d("BackgroundService", "Service running")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
        job?.cancel()
        Log.d("BackgroundService", "Service stopped")
    }
}
