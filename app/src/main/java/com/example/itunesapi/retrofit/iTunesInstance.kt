package com.example.itunesapi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object iTunesInstance {
    val api by lazy {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(itunesapi::class.java)
    }
}