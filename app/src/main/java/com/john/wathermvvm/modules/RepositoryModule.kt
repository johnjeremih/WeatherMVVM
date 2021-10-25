package com.john.wathermvvm.modules

import com.john.wathermvvm.repository.Repository
import com.john.wathermvvm.repository.cache.forecast.CacheForecastDataSource
import com.john.wathermvvm.repository.cache.city.CacheCityDataSource
import com.john.wathermvvm.repository.mapper.UpdateCityMapper
import com.john.wathermvvm.repository.network.CityNetworkData
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
        cityCache: CacheCityDataSource,
        forecastCache: CacheForecastDataSource,
        networkData: CityNetworkData,
        updateCityMapper: UpdateCityMapper
    ): Repository {
        return Repository(cityCache, forecastCache, networkData,updateCityMapper)
    }
}