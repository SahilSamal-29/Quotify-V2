package com.example.quotify_v2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val quoteText: TextView get() = findViewById(R.id.quoteText)
    private val quoteAuthor: TextView get() = findViewById(R.id.quoteAuthor)

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
    }

    fun onPrevious(view: View) {
        mainViewModel.previousQuote()
    }
    fun onNext(view: View) {
        mainViewModel.nextQuote()
    }
    fun onShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.currentQuote.toString())
        startActivity(Intent.createChooser(intent, "Share this quote using"))
    }
}