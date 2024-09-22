package com.thejawnpaul.gptinvestor.features.company

import com.thejawnpaul.gptinvestor.features.company.db.CompanyDao
import com.thejawnpaul.gptinvestor.features.company.db.CompanyEntity
import com.thejawnpaul.gptinvestor.features.company.model.Company
import com.thejawnpaul.gptinvestor.features.company.model.CompanyDetails
import com.thejawnpaul.gptinvestor.features.company.model.CompanyFinancials
import com.thejawnpaul.gptinvestor.features.company.model.CompanyFinancialsRequest
import com.thejawnpaul.gptinvestor.features.company.model.SectorInput
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface CompanyRepository {
  fun getAllCompanies(): Flow<List<Company>>

  suspend fun getAllSector(): List<SectorInput>

  suspend fun getCompany(ticker: String): Company

  suspend fun getCompanyDetails(ticker: String): CompanyDetails

  suspend fun getCompanyFinancials(ticker: String): CompanyFinancials

  suspend fun getCompaniesInSector(sector: String?): List<Company>
}

class CompanyRepositoryImpl
@Inject
constructor(private val companyDao: CompanyDao, private val companyService: CompanyService) :
  CompanyRepository {

  override fun getAllCompanies(): Flow<List<Company>> =
    channelFlow {
        launch {
          companyDao
            .getAllCompaniesFlow()
            .map { entityList ->
              entityList.map { entity ->
                with(entity) {
                  Company(ticker = ticker, name = name, summary = summary, logo = logoUrl)
                }
              }
            }
            .collect(::send)
        }
        val cacheModel =
          companyService.getCompanies().map { model ->
            with(model) {
              CompanyEntity(
                ticker = ticker,
                summary = summary,
                industry = industry,
                industryKey = industryKey,
                sector = sector,
                sectorKey = sectorKey,
                country = country,
                name = name,
                logoUrl = logoUrl,
                website = website,
                date = date,
              )
            }
          }
        companyDao.insertAll(cacheModel)
      }
      .filter(List<Company>::isNotEmpty)

  override suspend fun getAllSector(): List<SectorInput> {
    val allSector = listOf(SectorInput.AllSector)
    val companyList = companyDao.getAllCompanies()
    val defaultSectors =
      listOf(
        SectorInput.CustomSector("Technology", sectorKey = "technology"),
        SectorInput.CustomSector("Healthcare", sectorKey = "healthcare"),
        SectorInput.CustomSector("Energy", sectorKey = "energy"),
        SectorInput.CustomSector("Financial Services", sectorKey = "financial-services"),
        SectorInput.CustomSector("Real Estate", sectorKey = "real-estate"),
      )
    return if (companyList.isEmpty()) {
      allSector + defaultSectors
    } else {
      val sectors =
        companyList
          .map { entity ->
            SectorInput.CustomSector(sectorName = entity.sector, sectorKey = entity.sectorKey)
          }
          .filterNot { it.sectorName.lowercase() == "n/a" }
          .toSet()
          .take(5)
      allSector + sectors
    }
  }

  override suspend fun getCompany(ticker: String): Company =
    with(companyDao.getCompany(ticker)) {
      Company(ticker = ticker, name = name, summary = summary, logo = logoUrl)
    }

  override suspend fun getCompanyDetails(ticker: String): CompanyDetails =
    with(companyDao.getCompany(ticker)) {
      CompanyDetails(
        company = Company(ticker = ticker, name = name, summary = summary, logo = logoUrl),
        sector = sector,
        industry = industry,
      )
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

  override suspend fun getCompaniesInSector(sector: String?): List<Company> {
    val companies =
      if (sector == null) {
        companyDao.getAllCompanies()
      } else {
        companyDao.getCompaniesInSector(sector)
      }
    return companies.map { entity ->
      with(entity) { Company(ticker = ticker, name = name, summary = summary, logo = logoUrl) }
    }
  }
}
