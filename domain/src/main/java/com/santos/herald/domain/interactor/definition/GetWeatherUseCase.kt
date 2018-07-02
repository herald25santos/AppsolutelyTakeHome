package com.santos.herald.domain.interactor.definition

import com.santos.herald.domain.model.WeatherModel
import io.reactivex.Single

interface GetWeatherUseCase {
    fun execute(ids: String): Single<List<WeatherModel>>
}