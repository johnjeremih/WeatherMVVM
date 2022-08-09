package com.john.wathermvvm.modules

import com.john.wathermvvm.data.remote.RemoteDataSource
import com.john.wathermvvm.data.remote.RemoteDataSourceImpl
import com.john.wathermvvm.data.service.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherService: WeatherService,
    ): RemoteDataSource {
        return RemoteDataSourceImpl(weatherService = weatherService)
    }
}
