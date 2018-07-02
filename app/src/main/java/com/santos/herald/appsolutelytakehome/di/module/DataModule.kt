package com.santos.herald.appsolutelytakehome.di.module

import com.santos.herald.data.repository.WeatherRepositoryImpl
import com.santos.herald.data.source.RemoteSource
import com.santos.herald.data.source.remote.WeatherRemoteSource
import com.santos.herald.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    internal fun weatherRepository(repositoryImpl: WeatherRepositoryImpl): WeatherRepository {
        return repositoryImpl
    }

    @Provides
    @Singleton
    internal fun provideRemoteSource(retrofit: Retrofit): RemoteSource {
        return WeatherRemoteSource(retrofit)
    }

}
