package com.example.briefly.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.briefly.data.model.NewsArticle
import com.example.briefly.data.repository.NewsRepository
import com.example.briefly.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {
    // 1. Backing Property: Private so only this ViewModel can modify it
    private val _newsState = MutableStateFlow<Resource<List<NewsArticle>>>(Resource.Loading())

    // 2. Public Property: Read-only for the UI to observe
    val newsState: StateFlow<Resource<List<NewsArticle>>> = _newsState

    init {
        // 3. Fetch news automatically when the ViewModel is created
        fetchNews("general")
    }

    fun fetchNews(category: String) {
        viewModelScope.launch {
            repository.getTopHeadlines(category).collect { result ->
                _newsState.value = result
            }
        }
    }
}

// Things to take down:
/*
1. The ViewModel is the brain of the screen. It asks the Repository for news and holds the data so the UI can just "react" to it.
Code,Explanation
2.@HiltViewModel,"Tells Hilt: ""This is a ViewModel. Please prepare it for injection."""
3. @Inject constructor(...),"""I need the NewsRepository to do my job. Please find it in the RepositoryModule and give it to me."""
4. MutableStateFlow,Think of this as a Live Data Box. It currently holds Resource.Loading. We can change what's inside this box.
5. StateFlow (Public),"This is a Glass Window into that box. The UI can look at the data, but it cannot change it directly. This protects your data."
6. init { ... },This block runs immediately when the app starts this screen. We call fetchNews right away so the user doesn't see a blank screen.
7. viewModelScope.launch,"This starts a ""Coroutines"" thread that lives as long as the screen is open. If the user closes the app, this creates a clean cancel."
8. .collect { ... },"The Repository returns a stream (Flow). We ""collect"" (listen to) that stream. When data arrives (Success or Error), we update our _newsState."
 */