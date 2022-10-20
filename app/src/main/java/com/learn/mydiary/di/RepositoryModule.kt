package com.learn.mydiary.di

import com.learn.mydiary.data.repository.UserRepository
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.util.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(apiService: ApiService, preferences: Preferences): UserRepository = UserRepository(apiService)
}