package com.thejawnpaul.gptinvestor.homeimpl

import com.thejawnpaul.gptinvestor.home.db.CompanyInfo
import com.thejawnpaul.gptinvestor.home.db.HomeDao
import com.thejawnpaul.gptinvestor.homeimpl.model.CompanyData
import com.thejawnpaul.gptinvestor.homeimpl.model.SectorInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeRepository {
  fun getAllCompanies(): Flow<List<CompanyData>>

  suspend fun getAllSector(): List<SectorInput>

  suspend fun getCompaniesInSector(sector: String?): List<CompanyData>
}

class HomeRepositoryImpl
@Inject
constructor(private val homeDao: HomeDao, private val homeService: HomeService) : HomeRepository {

  override fun getAllCompanies(): Flow<List<CompanyData>> =
    channelFlow {
        launch {
          homeDao
            .getAllCompaniesFlow()
            .map { entityList ->
              entityList.map { entity ->
                with(entity) {
                  CompanyData(
                    ticker = ticker,
                    name = name,
                    logo = logoUrl,
                    sector = sector,
                    industry = industry,
                  )
                }
              }
            }
            .collect(::send)
        }
        val cacheModel =
          homeService.getCompanies().map { model ->
            with(model) {
              CompanyInfo(
                ticker = ticker,
                sector = sector,
                sectorKey = sectorKey,
                name = name,
                logoUrl = logoUrl,
                industry = industry,
              )
            }
          }
        homeDao.insertAll(cacheModel)
      }
      .filter { it.isNotEmpty() }

  override suspend fun getAllSector(): List<SectorInput> {
    val allSector = listOf(SectorInput.AllSector)
    val companyList = homeDao.getAllCompanies()
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

  override suspend fun getCompaniesInSector(sector: String?): List<CompanyData> {
    val companies =
      if (sector == null) {
        homeDao.getAllCompanies()
      } else {
        homeDao.getCompaniesInSector(sector)
      }
    return companies.map { entity ->
      with(entity) {
        CompanyData(
          ticker = ticker,
          name = name,
          logo = logoUrl,
          sector = this.sector,
          industry = industry,
        )
      }
    }
  }
}
