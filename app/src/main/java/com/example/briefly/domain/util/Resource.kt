package com.example.briefly.domain.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}

// Things to take down:
/*
 1. The Repository is the most important pattern in Android. The UI never talks to the API directly. It asks the Repository, and the Repository decides where to get data (Internet or Database).
 2. <T> (Generic): This means Resource can hold any data—a List<NewsArticle>, a String, or a Boolean.
 3. Success: Holds the data (e.g., the list of news).
 4. Error: Holds an error message (e.g., "No Internet").
 5. Loading: Tells the UI to show a spinner.
 6. A "Sealed Class" is like a super-enum. It can only be one of these 3 types.
*/