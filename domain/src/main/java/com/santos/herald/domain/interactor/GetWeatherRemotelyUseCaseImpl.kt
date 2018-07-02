package com.santos.herald.domain.interactor

import com.petrulak.cleankotlin.domain.executor.SchedulerProvider
import com.santos.herald.domain.interactor.definition.GetWeatherRemotelyUseCase
import com.santos.herald.domain.model.WeatherModel
import com.santos.herald.domain.repository.WeatherRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherRemotelyUseCaseImpl
@Inject
constructor(
        private val schedulerProvider: SchedulerProvider,
        private val repository: WeatherRepository
) : GetWeatherRemotelyUseCase {

    override fun executeGetWeatherList(ids: String, lat: String, lon: String): Single<List<WeatherModel>> {
        return when (ids.isEmpty()) {
            true -> Single.error(IllegalArgumentException("Wrong parameters"))
            false -> repository.getWeatherRemotely(ids, lat, lon)
        }.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
    }

    override fun executeGetWeather(id: String): Single<WeatherModel> {
        return when (id.isEmpty()) {
            true -> Single.error(IllegalArgumentException("Wrong parameters"))
            false -> repository.getWeatherByIdRemotely(id)
        }.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
    }

}
