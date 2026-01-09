package com.example.briefly.data.repository

import com.example.briefly.data.model.NewsArticle
import com.example.briefly.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // Get live news (Returns a stream of Loading -> Success/Error)
    suspend fun getTopHeadlines(category: String): Flow<Resource<List<NewsArticle>>>
}

// Things to take down:
/*
1. Location: data/repository/NewsRepository.kt Concept: This is the "Job Description" for our manager. It lists what the manager can do, but not how.

 */