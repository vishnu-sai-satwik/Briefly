package com.example.briefly.data.model
// This annotation helps if the JSON name is different from your variable name
// e.g. JSON has "urlToImage", but we want to use "imageUrl"


data class NewsArticle(
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null, // <--- This fixes the first error
    val source: Source? = null,     // <--- This fixes the second error
    val content: String? = null
)

data class Source(
    val id: String? = null,
    val name: String? = null
)
// Things to note down:
/*
 1. Data Class:
 A class specifically for holding data.
 It gives you useful methods like .toString() and .copy() for free.
 2. @SerializedName:
 This tells the GSON converter: "Hey, look for 'urlToImage' in the JSON, but save it into my variable called imageUrl."
 3. ? (Nullable):
 API data is messy. Sometimes a title or image is missing.
 Using String? prevents your app from crashing if data is null.
 4. Location: data/model/NewsArticle.kt Concept:
 When the News API sends back data, it comes as JSON (text).
 We need to convert that text into a Kotlin Object so our code can understand it.
 */