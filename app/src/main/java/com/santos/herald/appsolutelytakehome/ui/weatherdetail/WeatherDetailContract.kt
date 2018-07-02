package com.santos.herald.appsolutelytakehome.ui.weatherdetail

import com.santos.herald.appsolutelytakehome.ui.base.BasePresenter
import com.santos.herald.appsolutelytakehome.ui.base.BaseView

interface WeatherDetailContract {
    interface View : BaseView<Presenter> {
        override fun attachPresenter(presenter: Presenter)
        var weatherDetailViewState: WeatherDetailViewState
    }

    interface Presenter : BasePresenter<View> {
        override fun attachView(view: View)
        fun getWeather(id: String)
    }
}