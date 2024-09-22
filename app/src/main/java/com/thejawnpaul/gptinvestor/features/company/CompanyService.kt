package com.thejawnpaul.gptinvestor.features.company

import com.thejawnpaul.gptinvestor.features.company.model.CompanyFinancialsRemote
import com.thejawnpaul.gptinvestor.features.company.model.CompanyFinancialsRequest
import com.thejawnpaul.gptinvestor.features.company.model.CompanyRemote
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CompanyService {
  @GET("companies") suspend fun getCompanies(): List<CompanyRemote>

  @POST("company")
  suspend fun getCompanyFinancials(@Body request: CompanyFinancialsRequest): CompanyFinancialsRemote
}
