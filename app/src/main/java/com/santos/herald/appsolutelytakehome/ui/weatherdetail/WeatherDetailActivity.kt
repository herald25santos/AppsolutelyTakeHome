package com.santos.herald.appsolutelytakehome.ui.weatherdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.santos.herald.appsolutelytakehome.App
import com.santos.herald.appsolutelytakehome.R
import com.santos.herald.appsolutelytakehome.di.component.DaggerViewComponent
import com.santos.herald.appsolutelytakehome.di.module.PresenterModule
import com.santos.herald.appsolutelytakehome.ui.base.BaseActivity
import com.santos.herald.appsolutelytakehome.utils.FolderPathUtils
import com.santos.herald.appsolutelytakehome.utils.SystemUtils
import com.santos.herald.domain.model.WeatherModel
import kotlinx.android.synthetic.main.activity_weather_detail.*
import kotlinx.android.synthetic.main.view_header.*
import javax.inject.Inject
import kotlin.properties.Delegates


class WeatherDetailActivity : BaseActivity(), WeatherDetailContract.View {

    private var presenter: WeatherDetailContract.Presenter? = null

    @Inject
    lateinit var systemUtils: SystemUtils

    private var id: String = ""

    override fun layoutId() = R.layout.activity_weather_detail

    override fun inject() = DaggerViewComponent.builder()
            .presenterModule(PresenterModule())
            .applicationComponent(App.instance.appComponent())
            .build().inject(this)

    override fun afterLayout(savedInstanceState: Bundle?) {
        super.afterLayout(savedInstanceState)
        val intent = intent
        id = intent.getStringExtra("id")

    }

    override fun onViewsBound() {
        presenter?.start()
        presenter?.getWeather(id)
        initializeViews()
    }

    override fun onDestroy() {
        presenter?.stop()
        super.onDestroy()
    }

    @Inject
    override fun attachPresenter(presenter: WeatherDetailContract.Presenter) {
        this.presenter = presenter
        this.presenter?.attachView(this)
    }

    override var weatherDetailViewState: WeatherDetailViewState by Delegates.observable<WeatherDetailViewState>(WeatherDetailViewState.Init(), { _, _, new ->
        processStateChange(new)
    })

    private fun processStateChange(aNew: WeatherDetailViewState) {
        //using `when` as a statement, forces us to implement all possible values of `WeatherListViewState`
        return when (aNew) {
            is WeatherDetailViewState.Init -> initialize()
            is WeatherDetailViewState.Loading -> loading()
            is WeatherDetailViewState.Success -> onNewItem(aNew.model)
            is WeatherDetailViewState.Error -> onError(aNew.error)
            is WeatherDetailViewState.LoadingFinished -> finishLoading()
        }
    }

    private fun initializeViews() {
        tvDate.text = systemUtils.getCurrentDate()
        tvDay.text = systemUtils.getDayOfWeek()

        imgRefresh.setOnClickListener {
            presenter?.getWeather(id)
        }
    }

    private fun initialize() {

    }

    private fun loading() {
        constraintContent.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun onNewItem(model: WeatherModel) {
        tvWeatherName.text = model.name
        tvDesc.text = model.weather[0].description
        tvTemp.text = (model.main.temp.toString() + 0x00B0.toChar())
        tvPressure.text = ("Pressure: ${model.main.pressure}")
        tvHumidity.text = ("Humidity: ${model.main.humidity}")
        tvMinTemp.text = ("Min Temperature: ${model.main.temp_min}${0x00B0.toChar()}")
        tvMaxTemp.text = ("Max Temperature: ${model.main.temp_max}${0x00B0.toChar()}")

        tvSpeed.text = ("Speed: ${model.wind.speed}")
        tvDegree.text = ("Degree: ${model.wind.deg}")

        Glide.with(this)
                .load(FolderPathUtils.setIconWeather(model.weather[0].icon) + FolderPathUtils.EXT_PNG)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgWeather)
    }

    private fun finishLoading() {
        progressBar.visibility = View.GONE
        constraintContent.visibility = View.VISIBLE
    }

    private fun onError(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
    }

}
