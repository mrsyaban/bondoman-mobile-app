package com.pbd.psi

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pbd.psi.models.BaseResponse
import com.pbd.psi.models.LoginReq
import com.pbd.psi.models.LoginRes
import com.pbd.psi.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application){
    val userRepo = UserRepository()
    val loginResult : MutableLiveData<BaseResponse<LoginRes>> = MutableLiveData()

    fun loginUser(email: String, password: String){
        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginReq = LoginReq(
                    email = email,
                    password = password
                )
                val response = userRepo.loginUser(loginReq=loginReq)
                if(response.isSuccessful){
                    Log.d("LoginViewModel", "berhasillll")
                    loginResult.value = BaseResponse.Success(data = response.body())
                }else{
                    Log.d("LoginViewModel", "gagal")
                    loginResult.value = BaseResponse.Error(msg = response.message())

                }
                Log.d("LoginViewModel", "loginUser: ${response.body()}")
            } catch (e: Exception) {
                Log.d("LoginViewModel", "loginUser: ${e.message}")
                loginResult.value = BaseResponse.Error(msg = e.message)
            }
        }
    }
}