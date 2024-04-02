package com.pbd.psi.repository

import com.pbd.psi.api.ApiConfig
import com.pbd.psi.models.AuthRes
import com.pbd.psi.models.LoginReq
import com.pbd.psi.models.LoginRes
import retrofit2.Response

class UserRepository {
    suspend fun loginUser(loginReq : LoginReq) : Response<LoginRes> {
        return ApiConfig.getApiService().login(loginReq)
    }

    suspend fun checkAuth(token:String) : Response<AuthRes> {
        return ApiConfig.getApiService().auth(token)
    }
}