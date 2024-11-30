package com.example.quotify_v2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class QuoteFragment : Fragment() {
    private val quoteViewModel: QuoteViewModel by viewModels()
    private val quoteAdapter = QuoteAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentQuoteBinding.inflate(inflater, container, false)
        binding.quoteRecycler.adapter = quoteAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quoteViewModel.quotes.observe(viewLifecycleOwner) { quotes ->
            quoteAdapter.submitList(quotes)
        }
    }
}