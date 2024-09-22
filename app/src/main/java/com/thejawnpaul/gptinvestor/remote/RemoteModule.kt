package com.thejawnpaul.gptinvestor.remote

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
  @Provides fun provideRetrofit(remoteFactory: RemoteFactory): Retrofit = remoteFactory.retrofit

  @[Provides Singleton]
  fun provideMoshi(): Moshi = Moshi.Builder().build()
}
