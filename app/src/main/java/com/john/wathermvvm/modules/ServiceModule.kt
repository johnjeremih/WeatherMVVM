package com.john.wathermvvm.modules

import com.john.wathermvvm.repository.network.CityNetworkData
import com.john.wathermvvm.repository.network.CityNetworkDataImpl
import com.john.wathermvvm.repository.network.WeatherService
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
    ): CityNetworkData {
        return CityNetworkDataImpl(weatherService = weatherService)
    }

}