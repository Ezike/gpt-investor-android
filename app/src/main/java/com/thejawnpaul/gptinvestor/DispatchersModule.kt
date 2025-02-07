package com.thejawnpaul.gptinvestor

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(ViewModelComponent::class)
@Module
object DispatchersModule {
  @Provides fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
