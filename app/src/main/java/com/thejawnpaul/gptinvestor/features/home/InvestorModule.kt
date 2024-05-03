package com.thejawnpaul.gptinvestor.features.home

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@[Module InstallIn(ViewModelComponent::class)]
interface InvestorModule {
    @Binds
    fun provideInvestorRepository(repository: InvestorRepositoryImpl): InvestorRepository

    companion object {
        @Provides
        fun provideInvestorService(retrofit: Retrofit): InvestorService =
            retrofit.create(InvestorService::class.java)

        @Provides
        fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}
