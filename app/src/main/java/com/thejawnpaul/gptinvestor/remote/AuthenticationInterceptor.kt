package com.thejawnpaul.gptinvestor.remote

import com.thejawnpaul.gptinvestor.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object AuthenticationInterceptor : Interceptor {

  private const val TOKEN_TYPE = "Bearer "
  private const val AUTH_HEADER = "Authorization"

  override fun intercept(chain: Interceptor.Chain): Response {
    val token = BuildConfig.ACCESS_TOKEN.takeIf { it.isNotEmpty() }
    val request = chain.request()
    return if (token != null) {
      val interceptedRequest =
        chain.request().newBuilder().header(AUTH_HEADER, TOKEN_TYPE + token).build()
      chain.proceed(interceptedRequest)
    } else {
      chain.proceed(request)
    }
  }
}
