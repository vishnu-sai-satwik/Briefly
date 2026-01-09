package com.example.briefly.data.repository

import com.example.briefly.data.model.NewsArticle
import com.example.briefly.data.remote.NewsApi
import com.example.briefly.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi, // Hilt injects Retrofit API
    private val firestore: FirebaseFirestore // Hilt injects Firestore
) : NewsRepository {
    override suspend fun getTopHeadlines(category: String): Flow<Resource<List<NewsArticle>>> {
        return flow {
            emit(Resource.Loading(true)) // 1. Emit "Loading" state immediately so UI shows a spinner
            try {
                val response = api.getHeadlines(category = category) // 2. Make the Network Call
                // 3. Emit "Success" with the data
                // Note: We check if articles list is empty just in case
                if (response.articles.isNotEmpty()) {
                    emit(Resource.Success(response.articles))
                } else {
                    emit(Resource.Error("No news found"))
                }
                // 4. If crash/no internet, Emit "Error" with message
            } catch (e: Exception) {
                emit(Resource.Error(message = "Error: ${e.localizedMessage}"))
            }
        }
    }
}

// Things to take down:
/*
1. @Inject constructor: This is Hilt magic.
It tells Hilt: "When you create this class, please look for NewsApi and FirebaseFirestore in the AppModule and give them to me."
2. Flow: Think of a Flow like a pipe. You don't just return one value; you can send multiple values over time.
-> First, we send Loading.
-> Then, we wait for the internet...
-> Then, we send Success (Data) OR Error.
3. emit(...): This is how we push data into the pipe. The UI collects these emissions.
4. try/catch: Network calls are risky (phone might be offline).
We wrap it in try so the app doesn't crash. If it fails, we catch the error and tell the UI gracefully.
5. Location: data/repository/NewsRepositoryImpl.kt Concept: This is the actual Manager.
It takes the API and Database as tools (injected by Hilt) and does the work.
 */