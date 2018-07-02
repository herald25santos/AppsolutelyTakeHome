package com.santos.herald.appsolutelytakehome.ui.weatherlist

import com.santos.herald.domain.model.WeatherModel


sealed class WeatherListViewState {
    class Init : WeatherListViewState()
    class Loading : WeatherListViewState()
    class Success(val list: List<WeatherModel>) : WeatherListViewState()
    class LoadingFinished : WeatherListViewState()
    class Error(val error: Throwable) : WeatherListViewState()
}