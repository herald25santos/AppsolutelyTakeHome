package com.santos.herald.appsolutelytakehome.ui.weatherlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.santos.herald.appsolutelytakehome.R
import com.santos.herald.appsolutelytakehome.common.OnItemClickListener
import com.santos.herald.appsolutelytakehome.ui.base.BaseAdapter
import com.santos.herald.appsolutelytakehome.utils.FolderPathUtils
import com.santos.herald.domain.model.WeatherModel
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherListAdapter(private val mContext: Context, data: List<WeatherModel>, withHeader: Boolean, withFooter: Boolean)
    : BaseAdapter<WeatherModel>(data, withHeader, withFooter) {

    private var mOnClickListener: OnItemClickListener? = null

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        null!!
    }

    override fun getFooterView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        null!!
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.get(position)?.let { data ->
            val itemViewHolder = holder as ViewHolder
            itemViewHolder.bind(data, mOnClickListener, position)
        }
    }

    fun setOnClickListener(mOnClickListener: OnItemClickListener) {
        this.mOnClickListener = mOnClickListener
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(weatherModel: WeatherModel, mOnClickListener: OnItemClickListener?, position: Int) {
            itemView.tvWeatherName.text = weatherModel.name
            itemView.tvDesc.text = weatherModel.weather[0].description
            itemView.tvTemp.text = (weatherModel.main.temp.toString() + 0x00B0.toChar())

            Glide.with(itemView.context)
                    .load(FolderPathUtils.setIconWeather(weatherModel.weather[0].icon) + FolderPathUtils.EXT_PNG)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.imgWeather)

            itemView.setOnClickListener { mOnClickListener?.onItemClick(itemView, position) }
        }

    }


}