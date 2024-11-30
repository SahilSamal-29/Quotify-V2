package com.example.quotify_v2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuotesRepository {
    private val quotesApi: QuotesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/c/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        quotesApi = retrofit.create(QuotesApi::class.java)
    }

    fun getQuotes() = quotesApi.getQuotes()
}