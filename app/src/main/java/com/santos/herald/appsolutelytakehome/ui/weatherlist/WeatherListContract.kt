package com.santos.herald.appsolutelytakehome.ui.weatherlist

import com.santos.herald.appsolutelytakehome.ui.base.BasePresenter
import com.santos.herald.appsolutelytakehome.ui.base.BaseView

interface WeatherListContract {
    interface View : BaseView<Presenter> {
        override fun attachPresenter(presenter: Presenter)
        var weatherListViewState: WeatherListViewState
    }

    interface Presenter : BasePresenter<View> {
        override fun attachView(view: View)
        fun getWeatherList(ids: String, lat: String, lon: String)
    }
}