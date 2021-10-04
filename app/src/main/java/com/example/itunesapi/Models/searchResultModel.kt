package com.example.itunesapi.Models

data class searchResultModel(
    val resultCount : String,
    val results : List<musicModel>
)