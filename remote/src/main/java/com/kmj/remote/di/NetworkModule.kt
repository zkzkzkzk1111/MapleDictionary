package com.kmj.remote.di

import com.kmj.remote.api.ApiService
import com.kmj.remote.api.ApiService1
import com.kmj.remote.api.createApiService
import com.kmj.remote.api.createApiService1
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

    @Provides
    @Singleton
    fun provideApiService1(
        @Named("baseUrl1") baseUrl1: String,
    ): ApiService1 = createApiService1(baseUrl1)

    /*
    @Provides
    @Singleton
    fun provideApiService(
        @ApplicationContext context: Context
    ): ApiService = FakeApiService(context)
    */
}