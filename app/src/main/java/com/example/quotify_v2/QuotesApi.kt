package com.example.quotify_v2

import retrofit2.Call
import retrofit2.http.GET


interface QuotesApi {
    @GET("4fcb-6aa8-4240-9c6d")
    fun getQuotes(): Call<List<Quote>>
}