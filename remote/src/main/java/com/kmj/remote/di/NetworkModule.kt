package com.kmj.remote.di

import com.kmj.remote.api.ApiService
import com.kmj.remote.api.createApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(
        @Named("baseUrl") baseUrl: String,
    ): ApiService = createApiService(baseUrl)

    /*
    @Provides
    @Singleton
    fun provideApiService(
        @ApplicationContext context: Context
    ): ApiService = FakeApiService(context)
    */
}