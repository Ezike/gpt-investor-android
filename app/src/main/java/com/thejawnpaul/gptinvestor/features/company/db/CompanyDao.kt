package com.thejawnpaul.gptinvestor.features.company.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(company: CompanyEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(companies: List<CompanyEntity>)

  @Query("SELECT * FROM company_table ORDER BY ticker")
  suspend fun getAllCompanies(): List<CompanyEntity>

  @Query("SELECT * FROM company_table ORDER BY ticker")
  fun getAllCompaniesFlow(): Flow<List<CompanyEntity>>

  @Query("SELECT * FROM company_table WHERE ticker =:ticker")
  suspend fun getCompany(ticker: String): CompanyEntity

  @Query("SELECT * FROM company_table WHERE sectorKey =:sectorKey")
  suspend fun getCompaniesInSector(sectorKey: String): List<CompanyEntity>
}
