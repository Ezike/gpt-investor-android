package com.thejawnpaul.gptinvestor.homeimpl

import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.Page
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
interface HomeModule {

  @Binds fun bindRepository(impl: HomeRepositoryImpl): HomeRepository

  companion object {
    @Provides @IntoSet fun provideHomePage(navigator: Navigator): Page = homepage(navigator)

    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService =
      retrofit.create(HomeService::class.java)
  }
}
