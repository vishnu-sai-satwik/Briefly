package com.example.briefly.data.model

data class Summary(
    val id: String = "",
    val text: String = "",
    val bulletPoints: List<String> = emptyList()
)
