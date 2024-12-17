package com.example.quotify_v2

data class Quote(
    val id: Int,
    val author: String,
    val text: String
)

data class AIRequest(
    val model: String = "text-davinci-003",
    val prompt: String,
    val max_tokens: Int = 150,
    val n: Int = 5
)

data class AIResponse(val choices: List<Choice>)
data class Choice(val text: String)