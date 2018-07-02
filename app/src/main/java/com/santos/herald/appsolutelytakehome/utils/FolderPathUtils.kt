package com.santos.herald.appsolutelytakehome.utils
import com.santos.herald.appsolutelytakehome.BuildConfig

class FolderPathUtils {

    companion object {
        val EXT_PNG = ".png"
        val EXT_JPG = ".jpg"
        val EXT_JPEG = ".jpeg"

        fun setURLImage(image: String): String {
            return BuildConfig.HOST + "/" + image
        }

        fun setIconWeather(image: String): String {
            return BuildConfig.HOST + "img/w/" + image
        }

    }

}
