package com.john.weathermvvm.modules

import com.john.weathermvvm.repository.Repository
import com.john.weathermvvm.repository.cache.DataSource
import com.john.weathermvvm.repository.network.NetworkData
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