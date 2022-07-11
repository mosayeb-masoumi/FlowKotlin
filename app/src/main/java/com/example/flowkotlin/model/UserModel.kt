package com.example.flowkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("userId")
    @Expose
    val userId: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("completed")
    @Expose
    val completed :Boolean
)
