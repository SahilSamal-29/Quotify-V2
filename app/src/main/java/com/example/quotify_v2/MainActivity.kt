package com.example.quotify_v2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val quoteText: TextView get() = findViewById(R.id.quoteText)
    private val quoteAuthor: TextView get() = findViewById(R.id.quoteAuthor)
    private val nextButton: TextView get() = findViewById(R.id.next)
    private val previousButton: TextView get() = findViewById(R.id.previous)
    private val shareButton: FloatingActionButton get() = findViewById(R.id.shareButton)
    private val searchButton: Button get() = findViewById(R.id.searchButton)
    private val searchInput: TextView get() = findViewById(R.id.searchInput)
    private val apiKey = BuildConfig.OPENAI_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observe the current product
        mainViewModel.currentQuote.observe(this, Observer { quote ->
            if (quote != null) {
                quoteText.text = quote.text
                quoteAuthor.text = quote.author
            }
        })

        // Observe errors
        mainViewModel.error.observe(this, Observer { errorMessage ->
            quoteText.text = errorMessage
            quoteAuthor.text = ""
        })

        // Fetch the initial products
        mainViewModel.fetchQuotes()

        // Functionalities
        nextButton.setOnClickListener {
            mainViewModel.nextQuote()
        }
        previousButton.setOnClickListener {
            mainViewModel.previousQuote()
        }
        shareButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.currentQuote.toString())
            startActivity(Intent.createChooser(intent, "Share this quote using"))
        }
        searchButton.setOnClickListener {
            if (searchInput.text.toString().isNotEmpty()) {
                val query = searchInput.text.toString()
                fetchAIQuotes(query)
            }
        }
    }
    private fun fetchAIQuotes(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuotesApi::class.java)

            try {
                val response = apiService.generateQuotes(
                    apiKey = "Authorization: Bearer $apiKey",
                    request = AIRequest(prompt = "Generate 5 quotes about $query")
                )
                if (response.choices.isNotEmpty()) {
                    val quotes = response.choices.map { it.text.trim() }
                    withContext(Dispatchers.Main) {
                        displayQuotes(quotes)
                    }
                }else {
                    Log.e("API_ERROR", "Error Code: ${response}, Message: $response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //handle error
                withContext(Dispatchers.Main) {
                    quoteText.text = "Error: ${e.message}"
                }
                Log.e("API_ERROR", "Error Code: ${e.message}")
                Log.e("API_ERROR", "Error Code: ${e.cause}")
                Log.e("API_ERROR", "Error Code: ${e.localizedMessage}")
                Log.e("API_ERROR", "Error Code: ${e.stackTrace}")

            }
        }
    }
    private fun displayQuotes(quotes: List<String>) {
        var currentIndex = 0

        quoteText.text = quotes[currentIndex]

        nextButton.setOnClickListener {
            if (currentIndex < quotes.size - 1) {
                currentIndex++
                quoteText.text = quotes[currentIndex]
            }
        }

        previousButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                quoteText.text = quotes[currentIndex]
            }
        }
    }
}