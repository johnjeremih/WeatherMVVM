package com.john.wathermvvm.modules

import android.content.Context
import androidx.room.Room
import com.john.wathermvvm.repository.cache.CityDataSource
import com.john.wathermvvm.repository.cache.CityDataSourceImpl
import com.john.wathermvvm.repository.cache.Database
import com.john.wathermvvm.repository.cache.ForecastDataSource
import com.john.wathermvvm.repository.cache.ForecastDataSourceImpl
import com.john.wathermvvm.repository.cache.city.CityDao
import com.john.wathermvvm.repository.cache.city.CityDaoService
import com.john.wathermvvm.repository.cache.city.CityDaoServiceImpl
import com.john.wathermvvm.repository.cache.forecast.ForecastDao
import com.john.wathermvvm.repository.cache.forecast.ForecastDaoService
import com.john.wathermvvm.repository.cache.forecast.ForecastDaoServiceImpl
import com.john.wathermvvm.repository.mapper.CityMapper
import com.john.wathermvvm.repository.mapper.ForecastMapper
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
    ): CityDataSource {
        return CityDataSourceImpl(cityDaoService, cityMapper)
    }

    @Singleton
    @Provides
    fun provideForecastDataSource(
        forecastDaoService: ForecastDaoService,
        forecastMapper: ForecastMapper
    ): ForecastDataSource {
        return ForecastDataSourceImpl(forecastDaoService, forecastMapper)
    }

}
