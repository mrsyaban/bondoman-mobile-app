package com.pbd.psi.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log
import com.pbd.psi.LoginActivity
import com.pbd.psi.MainActivity
import com.pbd.psi.api.ApiConfig
import com.pbd.psi.models.AuthRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BackgroundService : Service() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var sharedpreferences: SharedPreferences
    private var isServiceRunning = false

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
        Thread {
            while (isServiceRunning) {
                try {
                    Thread.sleep(10000)
                    Log.d("BackgroundService", "Running")
                    ApiConfig.api.auth("Bearer $token").enqueue(object : Callback<AuthRes> {
                        override fun onResponse(call: Call<AuthRes>, response: Response<AuthRes>) {
                            if (response.isSuccessful){
                                Log.d("BackgroundService", "onResponse: ${response.body()}")
                            } else {
                                val editor = sharedpreferences.edit()
                                editor.clear()
                                editor.apply()
                                val loginIntent = Intent(this@BackgroundService, LoginActivity::class.java)
                                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(loginIntent)
                                stopSelf()
                            }
                        }

                        override fun onFailure(call: Call<AuthRes>, t: Throwable) {
                            Log.e("BackgroundService", "onFailure: ${t.message}")
                        }

                    })
                    Log.d("BackgroundService", "Service running")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
        Log.d("BackgroundService", "Service stopped")
    }
}
