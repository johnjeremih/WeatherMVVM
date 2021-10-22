package com.john.wathermvvm.modules

import com.google.gson.GsonBuilder
import com.john.wathermvvm.BuildConfig
import com.john.wathermvvm.repository.network.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideRecipeService(): WeatherService {

        // You can get your Key from this link https://rapidapi.com/weatherbit/api/weather/
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request()
                    .newBuilder()
                    .addHeader("x-rapidapi-host", "weatherbit-v1-mashape.p.rapidapi.com")
                    .addHeader(
                        "x-rapidapi-key",
                        BuildConfig.RAPIDAPI_KEY
                    )
                    .build()
            chain.proceed(request)
        }
        return Retrofit.Builder()
            .baseUrl("https://weatherbit-v1-mashape.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(httpClient.build())
            .build()
            .create(WeatherService::class.java)

    }

}

