package com.santos.herald.appsolutelytakehome.ui.weatherlist

import com.santos.herald.appsolutelytakehome.di.scope.ViewScope
import com.santos.herald.appsolutelytakehome.platform.extensions.getDisposableSingleObserver
import com.santos.herald.appsolutelytakehome.platform.extensions.getDisposableSubscriber
import com.santos.herald.appsolutelytakehome.ui.base.BasePresenterImpl
import com.santos.herald.appsolutelytakehome.ui.weatherlist.WeatherListContract.View
import com.santos.herald.domain.interactor.definition.GetWeatherRemotelyUseCase
import timber.log.Timber
import javax.inject.Inject


@ViewScope
class WeatherListPresenter
@Inject
constructor(private val getWeatherRemotelyUseCase: GetWeatherRemotelyUseCase) : BasePresenterImpl(), WeatherListContract.Presenter {

    var view: View? = null

    override fun attachView(view: View) {
        this.view = checkNotNull(view)
    }

    override fun start() {
        super.start()
    }

    override fun getWeatherList(ids: String, lat: String, lon: String) {
        val disposable = getWeatherRemotelyUseCase
                .executeGetWeatherList(ids, lat, lon)
                .doOnSubscribe { setViewState(WeatherListViewState.Loading()) }
                .doFinally { setViewState(WeatherListViewState.LoadingFinished()) }
                .subscribeWith(
                        getDisposableSingleObserver(
                                { setViewState(WeatherListViewState.Success(it)) },
                                { setViewState(WeatherListViewState.Error(it)) })
                )
        disposables.add(disposable)
    }

    private fun setViewState(stateWeatherList: WeatherListViewState) {
        view?.weatherListViewState = stateWeatherList
    }

    companion object {
        const val CITY_IDS = "5128581,1850147,2643743,1835848"
    }
}