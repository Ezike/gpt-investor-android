package com.thejawnpaul.gptinvestor.companyimpl.investor

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@[Module InstallIn(ViewModelComponent::class)]
interface InvestorModule {
  @Binds fun provideInvestorRepository(repository: InvestorRepositoryImpl): InvestorRepository

  companion object {
    @Provides
    fun provideInvestorService(retrofit: Retrofit): InvestorService =
      retrofit.create(InvestorService::class.java)
  }
}
