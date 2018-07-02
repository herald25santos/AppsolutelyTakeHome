package com.santos.herald.appsolutelytakehome.di.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.santos.herald.appsolutelytakehome.BuildConfig
import com.santos.herald.appsolutelytakehome.R
import com.santos.herald.data.source.remote.interceptor.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Module
class NetworkModule {

    @Provides
    @Named("prodOkhttpClient")
    @Singleton
    internal fun provideOkHttpClient(context: Context): OkHttpClient {

        val client = OkHttpClient.Builder()
        client.addInterceptor(RequestInterceptor(context.getString(R.string.weather_api_key)))

        if (BuildConfig.DEBUG) {

            client.addNetworkInterceptor(StethoInterceptor())
            //client.addNetworkInterceptor(TrafficStatInterceptor(1))
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logInterceptor)
        }

        return client.build()
    }

    @Provides
    @Singleton
    internal fun provideRestAdapter(context: Context, @Named("prodOkhttpClient") okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Named("unsafeOkhttpClient")
    @Singleton
    internal fun provideUnsafeOkhttpClient(context: Context): OkHttpClient {

        /* Trust anything*/
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        val client = OkHttpClient.Builder()
        client.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        client.hostnameVerifier { _, _ -> true }

        /* Rest of config*/
        client.addInterceptor(RequestInterceptor(context.getString(R.string.weather_api_key)))
        client.addNetworkInterceptor(StethoInterceptor())

        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        client.addInterceptor(logInterceptor)

        return client.build()
    }
}
