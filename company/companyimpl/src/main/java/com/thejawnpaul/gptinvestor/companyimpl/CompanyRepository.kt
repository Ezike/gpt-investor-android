package com.thejawnpaul.gptinvestor.companyimpl

import com.thejawnpaul.gptinvestor.company.db.CompanyDao
import com.thejawnpaul.gptinvestor.company.db.CompanyEntity
import com.thejawnpaul.gptinvestor.companyimpl.model.Company
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyFinancials
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyFinancialsRequest
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyInfoRequest
import javax.inject.Inject

interface CompanyRepository {

  suspend fun getCompany(ticker: String): Company

  suspend fun getCompanyFinancials(ticker: String): CompanyFinancials
}

class CompanyRepositoryImpl
@Inject
constructor(private val companyDao: CompanyDao, private val companyService: CompanyService) :
  CompanyRepository {

  override suspend fun getCompany(ticker: String): Company {
    val cacheData = companyDao.getCompany(ticker)
    return if (cacheData != null) {
      Company(ticker, cacheData.name, cacheData.summary, cacheData.logoUrl)
    } else {
      val response = companyService.getCompanyInfo(CompanyInfoRequest(ticker = ticker))
      val cacheModel =
        with(response) {
          CompanyEntity(ticker = ticker, summary = summary, name = name, logoUrl = logoUrl)
        }
      companyDao.insertCompany(cacheModel)
      Company(ticker, response.name, response.summary, response.logoUrl)
    }
  }

  override suspend fun getCompanyFinancials(ticker: String): CompanyFinancials {
    val request = CompanyFinancialsRequest(ticker = ticker, years = 1)
    return with(companyService.getCompanyFinancials(request)) {
      CompanyFinancials(
        open = open,
        high = high,
        low = low,
        close = close,
        volume = volume,
        marketCap = marketCap,
        currency = currency,
        news = news,
        historicalData = historicalData,
        balanceSheet = balanceSheet,
        financials = financials,
      )
    }
  }
}
