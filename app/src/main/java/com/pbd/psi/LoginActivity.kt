package com.pbd.psi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.pbd.psi.api.ApiConfig
import com.pbd.psi.databinding.ActivityLoginBinding
import com.pbd.psi.models.LoginReq
import com.pbd.psi.models.LoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL= "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        isLogin()

        binding.btnLogin.setOnClickListener {
            login()
        }
    }
    private fun isLogin(){
        val token = sharedpreferences.getString(TOKEN, "")
        if (token != null) {
            if (token.isNotEmpty()) {
                val intentRecycle = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intentRecycle)
                finish()
            }
        }
    }
    private fun login() {
        val email = binding.edtLoginEmail.text.toString()
        val password = binding.edtLoginPass.text.toString()

        val editor = sharedpreferences.edit()
        editor.putString(EMAIL, email)

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        val loginReq = LoginReq(email, password)
        ApiConfig.api.login(loginReq).enqueue(object : Callback<LoginRes> {
            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Login success", Toast.LENGTH_SHORT).show()
                    val token = response.body()?.token
                    editor.putString("TOKEN", token)
                    editor.apply()
                    val intentRecycle = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intentRecycle)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
            }
        })


    }
}