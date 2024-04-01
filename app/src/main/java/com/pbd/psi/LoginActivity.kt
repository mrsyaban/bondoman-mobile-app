package com.pbd.psi

import android.annotation.SuppressLint
import androidx.activity.viewModels
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.pbd.psi.databinding.ActivityLoginBinding
import com.pbd.psi.models.BaseResponse
import com.pbd.psi.models.LoginRes

class LoginActivity : AppCompatActivity() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL= "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedpreferences: SharedPreferences

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        isLogin()

        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    processError()
                    stopLoading()
                }
                else -> {
                    stopLoading()
                }
            }
        }

        binding.btnLogin.setOnClickListener{
            isOnline(this)
            doLogin()
        }
    }

    private fun processLogin(data : LoginRes?){
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
        val token = data?.token
        val editor = sharedpreferences.edit()
        editor.putString(TOKEN, token)
        editor.putString(EMAIL, binding.edtLoginEmail.text.toString())
        editor.apply()
        val intentRecycle = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intentRecycle)
        finish()
    }

    private fun doLogin(){
        val email = binding.edtLoginEmail.text.toString()
        val password = binding.edtLoginPass.text.toString()
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email and password must be filled", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.loginUser(email, password)
    }

    private fun isOnline(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return
            }
        }
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        return
    }
    private fun isLogin() {
        val token = sharedpreferences.getString(TOKEN, "")
        if (token?.isNotEmpty() == true) {
            val intentRecycle = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intentRecycle)
            finish()
        }
    }

    private fun showLoading(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun stopLoading(){
        binding.progressBar.visibility = View.GONE
    }

    private fun processError(){
        Toast.makeText(this, "Incorrect email or password.", Toast.LENGTH_SHORT).show()
    }

}