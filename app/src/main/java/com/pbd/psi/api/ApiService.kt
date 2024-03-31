package com.pbd.psi.api

import com.pbd.psi.models.AuthRes
import com.pbd.psi.models.LoginReq
import com.pbd.psi.models.LoginRes
import com.pbd.psi.models.UploadRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("/api/auth/login")
    fun login(
        @Body request: LoginReq
    ): Call<LoginRes>

    @POST("api/auth/token")
    fun auth(
        @Header("Authorization") authHeader: String
    ): Call<AuthRes>

    @Multipart
    @POST("/api/bill/upload")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Call<UploadRes>
}