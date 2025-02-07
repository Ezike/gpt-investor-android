package com.thejawnpaul.gptinvestor.homeimpl

import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.Page
import com.thejawnpaul.gptinvestor.remotetest.TestRemoteModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import retrofit2.Retrofit

@Module(includes = [TestRemoteModule::class])
@TestInstallIn(components = [SingletonComponent::class], replaces = [HomeModule::class])
class TestCompanyModule {

  @Provides fun provideHomeRepo(): HomeRepository = FakeHomeRepository()

  @Provides
  fun provideHomeService(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)

  @Provides @IntoSet fun provideHomePage(navigator: Navigator): Page = homepage(navigator)

  @OptIn(ExperimentalCoroutinesApi::class)
  @Provides
  fun provideTestDispatcher(): CoroutineDispatcher = UnconfinedTestDispatcher()
}
