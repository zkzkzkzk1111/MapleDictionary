package com.kmj.mapledictionary.di

import android.app.Application
import com.kmj.mapledictionary.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(application: Application): String =
        (application as MyApplication).baseUrl

    @Provides
    @Named("baseUrl1")
    fun provideBaseUrl1(application: Application): String =
        (application as MyApplication).baseUrl1

}