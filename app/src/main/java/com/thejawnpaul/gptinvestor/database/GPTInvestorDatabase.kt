package com.thejawnpaul.gptinvestor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thejawnpaul.gptinvestor.features.company.db.CompanyDao
import com.thejawnpaul.gptinvestor.features.company.db.CompanyEntity

@Database(entities = [CompanyEntity::class], version = 1, exportSchema = false)
abstract class GPTInvestorDatabase : RoomDatabase() {
  companion object {
    const val DB_NAME = "gptinvestor_database"
  }

  abstract fun companyDao(): CompanyDao
}
