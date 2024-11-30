package com.example.quotify_v2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val repository = QuotesRepository()

    private val _currentQuote = MutableLiveData<Quote?>()
    val currentQuote: LiveData<Quote?> get() = _currentQuote

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var quotesList: List<Quote> = emptyList()
    private var currentIndex: Int = 0

    fun fetchQuotes() {
        repository.getQuotes().enqueue(object : Callback<List<Quote>> {
            override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                if (response.isSuccessful) {
                    quotesList = response.body() ?: emptyList()
                    currentIndex = Random.nextInt(0, quotesList.size)
                    if (quotesList.isNotEmpty()) {
                        _currentQuote.value = quotesList[currentIndex]
                    } else {
                        _error.value = "No quotes available."
                    }
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                _error.value = t.message
            }
        })
    }

    fun nextQuote() {
        if (quotesList.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % quotesList.size // Loop to the first item
            _currentQuote.value = quotesList[currentIndex]
        }
    }

    fun previousQuote() {
        if (quotesList.isNotEmpty()) {
            currentIndex = if (currentIndex - 1 < 0) quotesList.size - 1 else currentIndex - 1
            _currentQuote.value = quotesList[currentIndex]
        }
    }
}