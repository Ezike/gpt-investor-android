package com.thejawnpaul.gptinvestor.remote

import com.squareup.moshi.Moshi
import com.thejawnpaul.gptinvestor.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RemoteFactory @Inject constructor(private val moshi: Moshi) {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .delegatingCallFactory { makeOkHttpClient() }
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private fun makeOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthenticationInterceptor)
            .build()
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun Retrofit.Builder.delegatingCallFactory(delegate: dagger.Lazy<OkHttpClient>): Retrofit.Builder =
        callFactory {
            delegate.get().newCall(it)
        }
}
