package com.example.briefly.data.di

import com.example.briefly.data.remote.GeminiApi
import com.example.briefly.data.remote.NewsApi
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // This module lives as long as the App lives
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/") // The main entry point
            .addConverterFactory(GsonConverterFactory.create()) // Converts JSON -> Kotlin
            .build()
    }
    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        // Retrofit creates the implementation of our interface here
        return retrofit.create(NewsApi::class.java)
    }
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
    @Provides
    @Singleton
    fun provideGeminiApi(): GeminiApi {
        return Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/") // Gemini's Home
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApi::class.java)
    }
}
// Things to take down:
/*
1. @Module / @InstallIn: Tells Hilt "This is a instruction manual for creating objects,"
and "Keep these objects alive for the whole app."
2. @Provides: The actual instruction. "If someone asks for a Retrofit object, run this function."
3. @Singleton: "Only create ONE instance of this." Essential for network clients to save resources.
4. Chaining: Notice provideNewsApi takes retrofit as a parameter? Hilt automatically finds the retrofit we created in the function above and injects it there!
5. Location: data/di/AppModule.kt
Concept: This is the "Engine Room". Instead of creating a new Retrofit() instance in every screen (which is bad for memory),
we create it once here, and Hilt gives it to anyone who needs it.
 */