package com.john.wathermvvm.modules

import com.john.wathermvvm.repository.network.NetworkData
import com.john.wathermvvm.repository.network.NetworkDataImpl
import com.john.wathermvvm.repository.service.WeatherService
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