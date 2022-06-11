package com.john.wathermvvm.modules

import com.john.wathermvvm.repository.cache.CityDataSource
import com.john.wathermvvm.repository.cache.ForecastDataSource
import com.john.wathermvvm.repository.network.NetworkData
import com.john.wathermvvm.repository.usecases.city.CityRepository
import com.john.wathermvvm.repository.usecases.city.CityRepositoryImpl
import com.john.wathermvvm.repository.usecases.forecast.ForecastRepository
import com.john.wathermvvm.repository.usecases.forecast.ForecastRepositoryImpl
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
  fun setCityRepository(
      cityDataSource: CityDataSource,
      forecastDataSource: ForecastDataSource,
      networkData: NetworkData
  ): CityRepository {
    return CityRepositoryImpl(cityDataSource, forecastDataSource, networkData)
  }

  @Singleton
  @Provides
  fun setForecastRepository(
      cityDataSource: CityDataSource,
      forecastDataSource: ForecastDataSource,
      networkData: NetworkData
  ): ForecastRepository {
    return ForecastRepositoryImpl(cityDataSource, forecastDataSource, networkData)
  }
}
