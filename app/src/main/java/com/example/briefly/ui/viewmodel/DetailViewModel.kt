package com.example.briefly.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.data.remote.Content
import com.example.briefly.data.remote.GeminiApi
import com.example.briefly.data.remote.GeminiRequest
import com.example.briefly.data.remote.Part
import com.example.briefly.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val geminiApi: GeminiApi,
    savedStateHandle: SavedStateHandle // Hilt grabs navigation args automatically!
) : ViewModel() {

    // Helper to store the Clean Title
    val articleTitle: String = URLDecoder.decode(
        savedStateHandle.get<String>("title") ?: "No Title",
        StandardCharsets.UTF_8.toString()
    )

    private val _summaryState = MutableStateFlow<Resource<String>>(Resource.Loading())
    val summaryState: StateFlow<Resource<String>> = _summaryState

    init {
        summarizeArticle(articleTitle)
    }

    private fun summarizeArticle(title: String) {
        viewModelScope.launch {
            _summaryState.value = Resource.Loading()
            try {
                // The Prompt we send to AI
                val prompt = "Analyze this news headline: '$title'. Provide a 3-point intelligence briefing. " +
                        "Start each point with a '*' character. " +
                        "The 3 points must be labelled exactly as: 'Context:', 'The Event:', and 'Implications:'. " +
                        "Keep each point concise (maximum 3 lines). " + // <--- Added Limit here
                        "Keep the tone professional, factual, and insightful."

                val request = GeminiRequest(listOf(Content(listOf(Part(prompt)))))

                // *** REPLACE WITH YOUR GEMINI API KEY ***
                val response = geminiApi.generateSummary("AIzaSyCeIPKeDR8aGmwk1zll_AzfScPrlUIfdVo", request)

                val resultText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text

                if (resultText != null) {
                    _summaryState.value = Resource.Success(resultText)
                } else {
                    _summaryState.value = Resource.Error("AI returned no data")
                }
            } catch (e: Exception) {
                _summaryState.value = Resource.Error("Failed to connect: ${e.message}")
            }
        }
    }
}