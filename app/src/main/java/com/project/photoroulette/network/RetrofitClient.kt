package com.alexhekmat.photoroulette.network

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit client code
 */
object RetrofitClient {
    private const val TAG = "RetrofitClient"
    private const val DEFAULT_TIMEOUT = 60L

    /**
     * Creates OkHttpClient
     */
    private val client = OkHttpClient.Builder()
        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request()
            try {
                val response = chain.proceed(request)
                response
            } catch (e: Exception) {
                throw e
            }
        }
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    /**
     * Create playlist API service with given URU
     */
    fun createPlaylistApiService(baseUrl: String): PlaylistApiService {
        val cleanBaseUrl = cleanBaseUrl(baseUrl)

        val retrofit = Retrofit.Builder()
            .baseUrl(cleanBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(PlaylistApiService::class.java)
    }

    private fun cleanBaseUrl(baseUrl: String): String {
        val urlWithoutQuery = if (baseUrl.contains("?")) {
            baseUrl.substringBefore("?")
        } else {
            baseUrl
        }

        val finalUrl = if (urlWithoutQuery.endsWith("/")) {
            urlWithoutQuery
        } else {
            "$urlWithoutQuery/"
        }

        return finalUrl
    }
}