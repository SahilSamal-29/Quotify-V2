package com.example.quotify_v2

class QuoteRepository(private val quoteService: QuoteService) {
    suspend fun getQuotes(): List<Quote> {
        val quoteResponse = quoteService.getQuotes()
        val quotes = quoteResponse.quotes.toMutableList()
        quotes.shuffle() // Randomize the order of the quotes
        return quotes
    }
}