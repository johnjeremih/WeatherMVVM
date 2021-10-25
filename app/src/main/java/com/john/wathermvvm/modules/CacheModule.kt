package com.john.wathermvvm.modules

import android.content.Context
import androidx.room.Room
import com.john.wathermvvm.model.City
import com.john.wathermvvm.repository.cache.city.*
import com.john.wathermvvm.repository.cache.forecast.*
import com.john.wathermvvm.repository.mapper.EntityMapper
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
    fun provideCitiesDb(@ApplicationContext context: Context): CityDatabase {
        return Room
            .databaseBuilder(
                context,
                CityDatabase::class.java,
                CityDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideForecastDb(@ApplicationContext context: Context): ForecastDatabase {
        return Room
            .databaseBuilder(
                context,
                ForecastDatabase::class.java,
                ForecastDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCitiesDAO(cityDatabase: CityDatabase): CityDao {
        return cityDatabase.weatherDao()
    }

    @Singleton
    @Provides
    fun provideForecastDAO(forecastDatabase: ForecastDatabase): ForecastDao {
        return forecastDatabase.forecastDao()
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
    fun provideCacheDataSource(
        cityDaoService: CityDaoService,
    ): CacheCityDataSource {
        return CacheCityDataSourceImpl(cityDaoService)
    }


    @Singleton
    @Provides
    fun provideForecastCacheDataSource(
        forecastDaoService: ForecastDaoService,
        cacheMapper: ForecastMapper
    ): CacheForecastDataSource {
        return CacheForecastDataSourceImpl(forecastDaoService, cacheMapper)
    }

}

























