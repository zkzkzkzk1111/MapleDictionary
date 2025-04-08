package com.kmj.remote.di

import com.kmj.data.remote.MapleStoryRemoteDataSource
import com.kmj.remote.impl.MapleStoryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule{
    @Binds
    @Singleton
    abstract fun bindMapleStoryRemoteDataSource(source: MapleStoryRemoteDataSourceImpl) : MapleStoryRemoteDataSource

}