package com.john.weathermvvm.modules

import com.john.weathermvvm.repository.network.NetworkData
import com.john.weathermvvm.repository.network.NetworkDataImpl
import com.john.weathermvvm.repository.service.WeatherService
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
    fun provideRecipeRepository(
        weatherService: WeatherService,
    ): NetworkData {
        return NetworkDataImpl(weatherService = weatherService)
    }

}