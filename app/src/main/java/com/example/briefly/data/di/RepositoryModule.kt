package com.example.briefly.data.di

import com.example.briefly.data.repository.NewsRepository
import com.example.briefly.data.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}

// Things to take down:
/*
We created the Impl (Implementation), but Hilt doesn't know that NewsRepository (Interface) should use NewsRepositoryImpl (Implementation).
Go back to data/di/AppModule.kt and add this "Bind" logic.
Actually, for simplicity, we can just add a new Module file called RepositoryModule.kt inside data/di.
Why? Interfaces can't be instantiated. This tells Hilt: "If I ask for NewsRepository, give me the NewsRepositoryImpl."
UI asks Repository -> Repository uses API -> API returns JSON -> Repository emits Success.
 */