package com.thejawnpaul.gptinvestor.home.db

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "home_able")
data class CompanyInfo(
  @PrimaryKey val ticker: String,
  val sector: String,
  val sectorKey: String,
  val industry: String,
  val name: String,
  val logoUrl: String,
)

@Dao
interface HomeDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(companies: List<CompanyInfo>)

  @Query("SELECT * FROM home_able ORDER BY ticker") suspend fun getAllCompanies(): List<CompanyInfo>

  @Query("SELECT * FROM home_able ORDER BY ticker")
  fun getAllCompaniesFlow(): Flow<List<CompanyInfo>>

  @Query("SELECT * FROM home_able WHERE sectorKey =:sectorKey")
  suspend fun getCompaniesInSector(sectorKey: String): List<CompanyInfo>
}
