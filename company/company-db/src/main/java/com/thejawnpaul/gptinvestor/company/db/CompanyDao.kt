package com.thejawnpaul.gptinvestor.company.db

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "company_table")
data class CompanyEntity(
  @PrimaryKey val ticker: String,
  val summary: String,
  val name: String,
  val logoUrl: String,
)

@Dao
interface CompanyDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertCompany(company: CompanyEntity)

  @Query("SELECT * FROM company_table WHERE ticker =:ticker")
  suspend fun getCompany(ticker: String): CompanyEntity?
}
