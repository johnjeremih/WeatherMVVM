package com.john.wathermvvm.modules

import com.john.wathermvvm.repository.service.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
  @Singleton
  @Provides
  fun provideWeatherService(retrofit: Retrofit): WeatherService {
    return retrofit.create(WeatherService::class.java)
  }
}
