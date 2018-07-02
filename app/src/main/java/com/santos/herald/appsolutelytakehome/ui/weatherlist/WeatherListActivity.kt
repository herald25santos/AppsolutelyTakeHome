package com.santos.herald.appsolutelytakehome.ui.weatherlist

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.view.View
import android.widget.Toast
import com.santos.herald.appsolutelytakehome.App

import com.santos.herald.appsolutelytakehome.R
import com.santos.herald.appsolutelytakehome.common.OnItemClickListener
import com.santos.herald.appsolutelytakehome.di.component.DaggerViewComponent
import com.santos.herald.appsolutelytakehome.di.module.PresenterModule
import com.santos.herald.appsolutelytakehome.ui.base.BaseActivity
import com.santos.herald.appsolutelytakehome.ui.weatherdetail.WeatherDetailActivity
import com.santos.herald.appsolutelytakehome.utils.SystemUtils
import com.santos.herald.domain.model.WeatherModel
import kotlinx.android.synthetic.main.activity_weather_list.*
import kotlinx.android.synthetic.main.view_header.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates
import com.santos.herald.appsolutelytakehome.utils.GPSUtils


class WeatherListActivity : BaseActivity(), WeatherListContract.View, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, GPSUtils.OnSuccessGetGPSLocation {

    private var presenter: WeatherListContract.Presenter? = null

    @Inject lateinit var systemUtils: SystemUtils

    lateinit var weatherListAdapter: WeatherListAdapter

    private var mCurrentLocation: Location? = null

    private var gpsUtils: GPSUtils? = null

    override fun layoutId() = R.layout.activity_weather_list

    override fun inject() = DaggerViewComponent.builder()
            .presenterModule(PresenterModule())
            .applicationComponent(App.instance.appComponent())
            .build().inject(this)

    override fun afterLayout(savedInstanceState: Bundle?) {
        super.afterLayout(savedInstanceState)
        gpsUtils = GPSUtils(this)
        gpsUtils?.initLocationServices()
        gpsUtils?.startLocation()
        gpsUtils?.getLastLocation()
        gpsUtils?.setOnSuccessGetGPSLocation(this)

    }
    override fun onViewsBound() {
        presenter?.start()
        initializeViews()
    }

    override fun onDestroy() {
        presenter?.stop()
        if (gpsUtils?.isRequestingLocationUpdates()!!) {
            gpsUtils?.stopLocationUpdates()
        }
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        gpsUtils?.onActivityResult(requestCode, resultCode, data)
    }

    @Inject
    override fun attachPresenter(presenter: WeatherListContract.Presenter) {
        this.presenter = presenter
        this.presenter?.attachView(this)
    }

    override var weatherListViewState: WeatherListViewState by Delegates.observable<WeatherListViewState>(WeatherListViewState.Init(), { _, _, new ->
        processStateChange(new)
    })

    override fun onItemClick(view: View, position: Int) {
        val bundle = Bundle()
        bundle.putString("id", weatherListAdapter.data?.get(position)?.id.toString())
        navigator.navigate(this, WeatherDetailActivity::class.java, bundle)
    }

    override fun onLongItemClick(v: View, position: Int) {

    }

    override fun onRefresh() {
        if(mCurrentLocation != null){
            presenter?.getWeatherList(WeatherListPresenter.CITY_IDS, mCurrentLocation?.latitude.toString(), mCurrentLocation?.longitude.toString())
        } else {
            finishLoading()
        }
    }

    override fun onGetGPSLocation(location: Location) {
        Timber.d("lat: " + location.toString())
        Timber.d("lon: " + location.toString())
        mCurrentLocation = location
        if(mCurrentLocation != null){
            presenter?.getWeatherList(WeatherListPresenter.CITY_IDS, mCurrentLocation?.latitude.toString(), mCurrentLocation?.longitude.toString())
        }
    }

    override fun onFailedGetGPSLocation(error: String?) {
        Timber.e("Failed of getting gps location: $error")
    }

    private fun processStateChange(aNew: WeatherListViewState) {
        //using `when` as a statement, forces us to implement all possible values of `WeatherListViewState`
        return when (aNew) {
            is WeatherListViewState.Init -> initialize()
            is WeatherListViewState.Loading -> loading()
            is WeatherListViewState.Success -> onNewList(aNew.list)
            is WeatherListViewState.Error -> onError(aNew.error)
            is WeatherListViewState.LoadingFinished -> finishLoading()
        }
    }

    private fun initializeViews() {
        tvDate.text = systemUtils.getCurrentDate()
        tvDay.text = systemUtils.getDayOfWeek()
        rvWeather.setHasFixedSize(true)
        rvWeather.itemAnimator = DefaultItemAnimator()
        weatherListAdapter = WeatherListAdapter(this, listOf(), false, false)
        weatherListAdapter.setOnClickListener(this)
        srlWeather.setOnRefreshListener(this)
        rvWeather.adapter = weatherListAdapter

        imgRefresh.setOnClickListener {
            onRefresh()
        }
    }

    private fun initialize() {
        srlWeather.isRefreshing = false
    }

    private fun loading() {
        srlWeather.isRefreshing = true
    }

    private fun onNewList(list: List<WeatherModel>) {
        list.forEach { Timber.d("name: " + it.name) }
        weatherListAdapter.data = list
        weatherListAdapter.notifyDataSetChanged()
    }

    private fun finishLoading() {
        srlWeather.isRefreshing = false
    }

    private fun onError(e: Throwable) {
        tvNoResult.visibility = View.VISIBLE
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
    }

}
