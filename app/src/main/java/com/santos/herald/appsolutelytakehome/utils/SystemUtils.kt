package com.santos.herald.appsolutelytakehome.utils
import android.annotation.SuppressLint
import android.content.Context
import com.santos.herald.appsolutelytakehome.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

class SystemUtils(context: Context) {

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        //calendar.setTimeInMillis(SystemEntity.getServer_Time());
        val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy")
        return simpleDateFormat.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDayOfWeek(): String {
        val calendar = Calendar.getInstance()
        //calendar.setTimeInMillis(SystemEntity.getServer_Time());
        val simpleDateFormat = SimpleDateFormat("EEEE")
        return simpleDateFormat.format(calendar.time)
    }

}
