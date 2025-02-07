package com.thejawnpaul.gptinvestor.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
  @Singleton
  @Provides
  fun provideDataBase(@ApplicationContext context: Context): GPTInvestorDatabase =
    Room.databaseBuilder(
        context.applicationContext,
        GPTInvestorDatabase::class.java,
        GPTInvestorDatabase.DB_NAME,
      )
      .fallbackToDestructiveMigration()
      .build()

  @Provides fun providesCompanyDao(db: GPTInvestorDatabase) = db.companyDao()

  @Provides fun providesHomeDao(db: GPTInvestorDatabase) = db.homeDao()
}
