package com.example.quotify_v2

import retrofit2.http.GET

interface QuoteService {
    @GET("api/quotes")
    suspend fun getQuotes(): QuoteResponse
}