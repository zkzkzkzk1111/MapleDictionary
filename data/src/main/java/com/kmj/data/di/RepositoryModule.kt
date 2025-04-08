package com.kmj.data.di

import com.kmj.data.repository.MapleStoryRepositoryImpl
import com.kmj.domain.repository.MapleStoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repo: MapleStoryRepositoryImpl): MapleStoryRepository

}