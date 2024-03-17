package com.pbd.psi.api

import com.pbd.psi.models.AuthRes
import com.pbd.psi.models.LoginReq
import com.pbd.psi.models.LoginRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {

    @POST("/api/auth/login")
    fun login(
        @Body request: LoginReq
    ): Call<LoginRes>

    @POST("api/auth/token")
    fun auth(
        @Header("Authorization") authHeader: String
    ): Call<AuthRes>
}