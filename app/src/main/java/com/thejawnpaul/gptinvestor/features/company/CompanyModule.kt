package com.thejawnpaul.gptinvestor.features.company

import com.thejawnpaul.gptinvestor.database.GPTInvestorDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@[Module InstallIn(ActivityRetainedComponent::class)]
interface CompanyModule {

  @Binds fun bindRepo(repo: CompanyRepositoryImpl): CompanyRepository

  companion object {
    @Provides
    fun provideCompanyService(retrofit: Retrofit): CompanyService =
      retrofit.create(CompanyService::class.java)

    @Provides fun providesCompanyDao(db: GPTInvestorDatabase) = db.companyDao()
  }
}
