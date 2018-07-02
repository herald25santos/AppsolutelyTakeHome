package com.santos.herald.appsolutelytakehome.ui.weatherdetail

import com.santos.herald.domain.model.WeatherModel


sealed class WeatherDetailViewState {
    class Init : WeatherDetailViewState()
    class Loading : WeatherDetailViewState()
    class Success(val model: WeatherModel) : WeatherDetailViewState()
    class LoadingFinished : WeatherDetailViewState()
    class Error(val error: Throwable) : WeatherDetailViewState()
}