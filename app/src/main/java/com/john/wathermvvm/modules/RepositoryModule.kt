package com.john.wathermvvm.modules

import com.john.wathermvvm.repository.Repository
import com.john.wathermvvm.repository.cache.DataSource
import com.john.wathermvvm.repository.network.NetworkData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGetWeather(
        cityCache: DataSource,
        networkData: NetworkData
    ): Repository {
        return Repository(cityCache, networkData)
    }
}