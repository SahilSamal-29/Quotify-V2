package com.example.quotify_v2

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AIQuoteService {
    @POST("v1/completions")
    suspend fun generateQuotes(
        @Header("Authorization") apiKey: String,
        @Body request: AIRequest
    ): AIResponse
}