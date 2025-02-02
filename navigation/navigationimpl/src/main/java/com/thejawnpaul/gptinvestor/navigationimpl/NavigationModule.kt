package com.thejawnpaul.gptinvestor.navigationimpl

import com.thejawnpaul.gptinvestor.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface NavigationModule {

  @Binds fun bindNavigator(navigator: AppNavigator): Navigator

  @Binds fun bindNavState(navigator: AppNavigator): NavState
}
