package com.santos.herald.appsolutelytakehome.di.module

import com.petrulak.cleankotlin.domain.executor.SchedulerProvider
import com.santos.herald.domain.interactor.GetWeatherRemotelyUseCaseImpl
import com.santos.herald.domain.interactor.GetWeatherUseCaseImpl
import com.santos.herald.domain.interactor.definition.GetWeatherRemotelyUseCase
import com.santos.herald.domain.interactor.definition.GetWeatherUseCase
import com.santos.herald.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class InteractorModule {

    @Singleton
    @Provides
    internal fun getWeatherRemotelyUseCase(schedulerProvider: SchedulerProvider, repository: WeatherRepository): GetWeatherRemotelyUseCase {
        return GetWeatherRemotelyUseCaseImpl(schedulerProvider, repository)
    }

    @Singleton
    @Provides
    internal fun getWeatherUseCase(schedulerProvider: SchedulerProvider, repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCaseImpl(schedulerProvider, repository)
    }
}
