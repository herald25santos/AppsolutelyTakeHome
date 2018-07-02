package com.santos.herald.domain.interactor

import com.petrulak.cleankotlin.domain.executor.SchedulerProvider
import com.santos.herald.domain.interactor.definition.GetWeatherUseCase
import com.santos.herald.domain.model.WeatherModel
import com.santos.herald.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherUseCaseImpl
@Inject
constructor(
        private val schedulerProvider: SchedulerProvider,
        private val repository: WeatherRepository
) : GetWeatherUseCase {

    private fun buildUseCase(city: String): Single<List<WeatherModel>> {
        return when (city.isEmpty()) {
            true -> Single.error(IllegalArgumentException("Wrong parameters"))
            false -> repository.getWeatherByIdsRemotely(city)
        }
    }

    override fun execute(ids: String): Single<List<WeatherModel>> {
        return buildUseCase(ids).subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
    }
}