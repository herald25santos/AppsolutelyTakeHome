package com.santos.herald.domain.repository

import com.santos.herald.domain.model.WeatherModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface WeatherRepository {
    fun getWeatherRemotely(ids: String, lat: String, lon: String): Single<List<WeatherModel>>
    fun getWeatherByIdsRemotely(ids: String): Single<List<WeatherModel>>
    fun getWeatherByIdRemotely(id: String): Single<WeatherModel>
    fun getWeatherByCoordinates(lat: String, lon: String): Single<List<WeatherModel>>
}
