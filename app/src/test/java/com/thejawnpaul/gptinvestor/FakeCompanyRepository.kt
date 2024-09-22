package com.thejawnpaul.gptinvestor

import com.thejawnpaul.gptinvestor.features.company.CompanyRepository
import com.thejawnpaul.gptinvestor.features.company.model.Company
import com.thejawnpaul.gptinvestor.features.company.model.CompanyDetails
import com.thejawnpaul.gptinvestor.features.company.model.CompanyFinancials
import com.thejawnpaul.gptinvestor.features.company.model.CompanyNews
import com.thejawnpaul.gptinvestor.features.company.model.NewsResolution
import com.thejawnpaul.gptinvestor.features.company.model.NewsThumbNail
import com.thejawnpaul.gptinvestor.features.company.model.SectorInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class FakeCompanyRepository : CompanyRepository {

  override fun getAllCompanies(): Flow<List<Company>> =
    flowOf(
      listOf(Company(ticker = "AAPL", name = "Apple Inc.", summary = "Technology", logo = "logo"))
    )

  override suspend fun getAllSector(): List<SectorInput> = listOf(SectorInput.AllSector)

  override suspend fun getCompany(ticker: String): Company = getAllCompanies().first().first()

  override suspend fun getCompanyDetails(ticker: String): CompanyDetails =
    CompanyDetails(company = getCompany(ticker), sector = "Technology", industry = "Technology")

  override suspend fun getCompanyFinancials(ticker: String): CompanyFinancials =
    CompanyFinancials(
      open = 1f,
      close = 2f,
      high = 3f,
      low = 4f,
      volume = 5L,
      currency = "USD",
      financials = "Financials",
      marketCap = 1L,
      balanceSheet = "Balance Sheet",
      historicalData = "Historical Data",
      news =
        listOf(
          CompanyNews(
            title = "News 1",
            link = "https://www.google.com",
            id = "1",
            type = "Article",
            publisher = "Google",
            providerPublishTime = 1L,
            relatedTickers = listOf("AAPL"),
            thumbNail =
              NewsThumbNail(
                resolutions =
                  listOf(
                    NewsResolution(
                      height = 1,
                      width = 3,
                      url = "https://www.google.com/logo.png",
                      tag = "img",
                    )
                  )
              ),
          )
        ),
    )

  override suspend fun getCompaniesInSector(sector: String?): List<Company> =
    listOf(Company(ticker = "Google", name = "Google Inc.", summary = "Technology", logo = "logo"))
}
