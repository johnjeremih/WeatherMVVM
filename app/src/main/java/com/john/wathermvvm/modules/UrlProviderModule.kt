package com.john.wathermvvm.modules

import com.john.wathermvvm.repository.network.UrlMainProvider
import com.john.wathermvvm.repository.network.UrlProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UrlProviderModule {


      @Singleton
      @Binds
      abstract fun provideUrlProvider(urlMainProvider: UrlMainProvider): UrlProvider

}