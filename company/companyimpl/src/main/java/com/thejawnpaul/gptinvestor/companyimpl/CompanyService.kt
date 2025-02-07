package com.thejawnpaul.gptinvestor.companyimpl

import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyFinancialsRemote
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyFinancialsRequest
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyInfoRequest
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyInfoResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CompanyService {

  @POST("company")
  suspend fun getCompanyFinancials(@Body request: CompanyFinancialsRequest): CompanyFinancialsRemote

  @POST("company-info")
  suspend fun getCompanyInfo(@Body request: CompanyInfoRequest): CompanyInfoResponse
}
