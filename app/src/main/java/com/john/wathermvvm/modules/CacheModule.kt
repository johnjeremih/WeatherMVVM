package com.john.wathermvvm.modules

import android.content.Context
import androidx.room.Room
import com.john.wathermvvm.data.local.datasource.city.LocalCityDataSource
import com.john.wathermvvm.data.local.datasource.city.LocalCityDataSourceImpl
import com.john.wathermvvm.data.local.Database
import com.john.wathermvvm.data.local.datasource.forecast.LocalForecastDataSource
import com.john.wathermvvm.data.local.datasource.forecast.LocalForecastDataSourceImpl
import com.john.wathermvvm.data.local.cache.city.CityDao
import com.john.wathermvvm.data.local.cache.city.CityDaoService
import com.john.wathermvvm.data.local.cache.city.CityDaoServiceImpl
import com.john.wathermvvm.data.local.cache.forecast.ForecastDao
import com.john.wathermvvm.data.local.cache.forecast.ForecastDaoService
import com.john.wathermvvm.data.local.cache.forecast.ForecastDaoServiceImpl
import com.john.wathermvvm.util.mapper.CityMapper
import com.john.wathermvvm.util.mapper.ForecastMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideCacheMapper(): ForecastMapper {
        return ForecastMapper()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room
            .databaseBuilder(
                context,
                Database::class.java,
                Database.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun provideCitiesDao(database: Database): CityDao {
        return database.weatherDao()
    }

    @Singleton
    @Provides
    fun provideForecastDao(database: Database): ForecastDao {
        return database.forecastDao()
    }

    @Singleton
    @Provides
    fun provideCityDaoService(
        cityDao: CityDao
    ): CityDaoService {
        return CityDaoServiceImpl(cityDao)
    }

    @Singleton
    @Provides
    fun provideForecastDaoService(
        forecastDao: ForecastDao
    ): ForecastDaoService {
        return ForecastDaoServiceImpl(forecastDao)
    }

    @Singleton
    @Provides
    fun provideCityDataSource(
        cityDaoService: CityDaoService,
        cityMapper: CityMapper
    ): LocalCityDataSource {
        return LocalCityDataSourceImpl(cityDaoService, cityMapper)
    }

    @Singleton
    @Provides
    fun provideForecastDataSource(
        forecastDaoService: ForecastDaoService,
        forecastMapper: ForecastMapper
    ): LocalForecastDataSource {
        return LocalForecastDataSourceImpl(forecastDaoService, forecastMapper)
    }
}
