package com.nick.lentagifv.network

import android.os.Build
import android.os.Build.MODEL
import android.util.DisplayMetrics
import com.nick.lentagifv.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NetworkService {

    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        }
    }

    private val baseInterceptor: Interceptor = invoke { chain ->

        val newUrl = chain
            .request()
            .url
            .newBuilder()
            .build()

        val display = "${DisplayMetrics().heightPixels}x${DisplayMetrics().widthPixels}"

        val request = chain.request().newBuilder().apply {
            addHeader("Accept", "application/json")
            addHeader(
                "User-agent",
                "lenta-gifv-app/${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE}) (${MODEL}; Android/${Build.VERSION.RELEASE}; ${Locale.getDefault()}; $display)"
            )
        }
            .url(newUrl)
            .build()

        return@invoke chain.proceed(request)
    }

    private val client: OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
                addInterceptor(baseInterceptor)
            }
            .build()

    fun retrofitService(): FeedApi {
        return Retrofit.Builder()
            .baseUrl("https://api.tjournal.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(FeedApi::class.java)
    }

}