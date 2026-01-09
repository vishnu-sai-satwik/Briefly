package com.example.briefly.data.remote
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

// 1. Define the shape of the Request we send to Gemini
data class GeminiRequest(val contents: List<Content>)
data class Content(val parts: List<Part>)
data class Part(val text: String)

// 2. Define the shape of the Response we get back
data class GeminiResponse(val candidates: List<Candidate>?)
data class Candidate(val content: Content?)
interface GeminiApi {
    // Note: No '/' at the start of the string below!
    @POST("v1beta/models/gemini-2.5-flash-preview-09-2025:generateContent")
    suspend fun generateSummary(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}