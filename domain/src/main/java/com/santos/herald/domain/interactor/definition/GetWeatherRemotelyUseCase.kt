package com.santos.herald.domain.interactor.definition

import com.santos.herald.domain.model.WeatherModel
import io.reactivex.Single

interface GetWeatherRemotelyUseCase {

    fun executeGetWeatherList(ids: String, lat: String, lon: String): Single<List<WeatherModel>>
    fun executeGetWeather(id: String): Single<WeatherModel>
}