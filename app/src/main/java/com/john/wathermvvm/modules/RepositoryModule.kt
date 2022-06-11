package com.john.wathermvvm.modules

import com.john.wathermvvm.data.local.datasource.city.LocalCityDataSource
import com.john.wathermvvm.data.local.datasource.forecast.LocalForecastDataSource
import com.john.wathermvvm.data.remote.RemoteDataSource
import com.john.wathermvvm.data.repositories.city.CityRepository
import com.john.wathermvvm.data.repositories.city.CityRepositoryImpl
import com.john.wathermvvm.data.repositories.forecast.ForecastRepository
import com.john.wathermvvm.data.repositories.forecast.ForecastRepositoryImpl
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
      localCityDataSource: LocalCityDataSource,
      localForecastDataSource: LocalForecastDataSource,
      remoteDataSource: RemoteDataSource
  ): CityRepository {
    return CityRepositoryImpl(localCityDataSource, localForecastDataSource, remoteDataSource)
  }

  @Singleton
  @Provides
  fun setForecastRepository(
      localCityDataSource: LocalCityDataSource,
      localForecastDataSource: LocalForecastDataSource,
      remoteDataSource: RemoteDataSource
  ): ForecastRepository {
    return ForecastRepositoryImpl(localCityDataSource, localForecastDataSource, remoteDataSource)
  }
}
