package com.example.briefly.data.model

data class NewsResponse (
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>
)
