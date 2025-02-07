package com.thejawnpaul.gptinvestor.companyimpl

import com.thejawnpaul.gptinvestor.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import retrofit2.Retrofit

@[Module InstallIn(ActivityRetainedComponent::class)]
interface CompanyModule {

  @Binds fun bindRepo(repo: CompanyRepositoryImpl): CompanyRepository

  companion object {
    @Provides
    fun provideCompanyService(retrofit: Retrofit): CompanyService =
      retrofit.create(CompanyService::class.java)

    @Provides @IntoSet fun provideCompanyPage(navigator: Navigator) = companyPage(navigator)

    @Provides @IntoSet fun provideNewsPage(navigator: Navigator) = newsPage(navigator)
  }
}
