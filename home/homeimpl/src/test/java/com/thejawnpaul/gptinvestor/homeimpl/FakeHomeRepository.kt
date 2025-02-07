package com.thejawnpaul.gptinvestor.homeimpl

import com.thejawnpaul.gptinvestor.homeimpl.model.CompanyData
import com.thejawnpaul.gptinvestor.homeimpl.model.SectorInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeHomeRepository : HomeRepository {

  override fun getAllCompanies(): Flow<List<CompanyData>> =
    flowOf(
      listOf(
        CompanyData(
          ticker = "AAPL",
          name = "Apple Inc.",
          logo = "logo",
          sector = "tech",
          industry = "tech",
        )
      )
    )

  override suspend fun getAllSector(): List<SectorInput> = listOf(SectorInput.AllSector)

  override suspend fun getCompaniesInSector(sector: String?): List<CompanyData> =
    listOf(
      CompanyData(
        ticker = "Google",
        name = "Google Inc.",
        logo = "logo",
        sector = "tech",
        industry = "tech",
      )
    )
}
