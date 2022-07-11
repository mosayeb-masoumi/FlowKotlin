package com.example.cleanarchitecture_xml.data.remote


import com.example.flowkotlin.model.UserModel
import com.google.gson.JsonObject
import retrofit2.Call

import retrofit2.http.GET

interface ApiService {

    @GET("todos/")
    suspend fun getUserList(): List<UserModel>



    @GET("/fake/api")
    fun refreshToken(): Call<JsonObject?>?
}