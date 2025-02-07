package com.thejawnpaul.gptinvestor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thejawnpaul.gptinvestor.company.db.CompanyDao
import com.thejawnpaul.gptinvestor.company.db.CompanyEntity
import com.thejawnpaul.gptinvestor.home.db.CompanyInfo
import com.thejawnpaul.gptinvestor.home.db.HomeDao

@Database(entities = [CompanyEntity::class, CompanyInfo::class], version = 1, exportSchema = false)
abstract class GPTInvestorDatabase : RoomDatabase() {
  companion object {
    const val DB_NAME = "gptinvestor_database"
  }

  abstract fun companyDao(): CompanyDao

  abstract fun homeDao(): HomeDao
}
