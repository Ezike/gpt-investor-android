package com.thejawnpaul.gptinvestor.features.home

import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.Page
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityComponent::class)
object HomeModule {
  @Provides @IntoSet fun provideHomePage(navigator: Navigator): Page = homepage(navigator)
}
