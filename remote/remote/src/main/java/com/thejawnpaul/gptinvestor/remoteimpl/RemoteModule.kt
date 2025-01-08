package com.thejawnpaul.gptinvestor.remoteimpl

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
  @Provides @Singleton fun provideRetrofit(moshi: Moshi): Retrofit = RetrofitFactory.create(moshi)

  @Provides @Singleton fun provideMoshi(): Moshi = Moshi.Builder().build()
}
