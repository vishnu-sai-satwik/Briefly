package com.example.briefly.data.remote

import com.example.briefly.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String = "us",
        @Query("Category") category: String = "general",
        @Query("apiKey") apiKey: String = "d4b364f57e9b44399fc7ac63780f5805"
    ): NewsResponse
}
// Things to note down:
/*
1. BASE_URL will be: "https://newsapi.org/v2/"
2. interface: A contract. It defines what can be done, but not how. Retrofit handles the how.
3. @GET("top-headlines"): This tells Retrofit to attach /top-headlines to the end of your Base URL.
4. suspend: This function will pause execution without freezing the app (Coroutines).
It runs on a background thread automatically.
5. @Query: This adds parameters to the URL.
It will look like this:Resulting URL: "https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=..."
6. Location:
data/remote/NewsApi.kt Concept: Retrofit (our network library) needs an Interface to know which URL to hit.
We don't write the actual network code; Retrofit generates it for us based on this file.
 */