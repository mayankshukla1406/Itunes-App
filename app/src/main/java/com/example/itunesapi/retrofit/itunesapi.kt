package com.example.itunesapi.retrofit

import com.example.itunesapi.Models.musicModel
import com.example.itunesapi.Models.searchResultModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface itunesapi {@GET("search?")
suspend fun getResult(@Query("term") search:String): Response<searchResultModel>

}