package com.santos.herald.appsolutelytakehome.ui.weatherdetail

import com.santos.herald.appsolutelytakehome.di.scope.ViewScope
import com.santos.herald.appsolutelytakehome.platform.extensions.getDisposableSingleObserver
import com.santos.herald.appsolutelytakehome.platform.extensions.getDisposableSubscriber
import com.santos.herald.appsolutelytakehome.ui.base.BasePresenterImpl
import com.santos.herald.appsolutelytakehome.ui.weatherdetail.WeatherDetailContract.View
import com.santos.herald.domain.interactor.definition.GetWeatherRemotelyUseCase
import timber.log.Timber
import javax.inject.Inject


@ViewScope
class WeatherDetailPresenter
@Inject
constructor(private val getWeatherRemotelyUseCase: GetWeatherRemotelyUseCase) : BasePresenterImpl(), WeatherDetailContract.Presenter {

    var view: View? = null

    override fun attachView(view: View) {
        this.view = checkNotNull(view)
    }

    override fun start() {
        super.start()
    }

    override fun getWeather(id: String) {
        val disposable = getWeatherRemotelyUseCase
                .executeGetWeather(id)
                .doOnSubscribe { setViewState(WeatherDetailViewState.Loading()) }
                .doFinally { setViewState(WeatherDetailViewState.LoadingFinished()) }
                .subscribeWith(
                        getDisposableSingleObserver(
                                { setViewState(WeatherDetailViewState.Success(it)) },
                                { setViewState(WeatherDetailViewState.Error(it)) })
                )
        disposables.add(disposable)
    }


    private fun setViewState(stateWeather: WeatherDetailViewState) {
        view?.weatherDetailViewState = stateWeather
    }

    companion object {
        const val CITY_IDS = "5128581,1850147,2643743,1835848"
    }
}