package com.thejawnpaul.gptinvestor

import com.thejawnpaul.gptinvestor.database.DatabaseModule
import com.thejawnpaul.gptinvestor.database.GPTInvestorDatabase
import com.thejawnpaul.gptinvestor.features.company.CompanyModule
import com.thejawnpaul.gptinvestor.features.company.CompanyRepository
import com.thejawnpaul.gptinvestor.features.company.CompanyService
import com.thejawnpaul.gptinvestor.features.home.InvestorModule
import com.thejawnpaul.gptinvestor.features.home.InvestorRepository
import com.thejawnpaul.gptinvestor.remote.RemoteModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import retrofit2.Retrofit

@Module(includes = [DatabaseModule::class, RemoteModule::class])
@TestInstallIn(components = [SingletonComponent::class], replaces = [CompanyModule::class])
class TestCompanyModule {

  @Provides fun provideCompanyRepo(): CompanyRepository = FakeCompanyRepository()

  @Provides
  fun provideCompanyService(retrofit: Retrofit): CompanyService =
    retrofit.create(CompanyService::class.java)

  @Provides fun providesCompanyDao(db: GPTInvestorDatabase) = db.companyDao()
}

@Module(includes = [DatabaseModule::class, RemoteModule::class])
@TestInstallIn(components = [SingletonComponent::class], replaces = [InvestorModule::class])
object TestInvestorModule {

  @Provides fun provideInvestorRepository(): InvestorRepository = FakeInvestorRepository()

  @OptIn(ExperimentalCoroutinesApi::class)
  @Provides
  fun provideCoroutineDispatcher(): CoroutineDispatcher = UnconfinedTestDispatcher()
}
