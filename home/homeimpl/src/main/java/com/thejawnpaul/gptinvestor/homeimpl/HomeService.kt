package com.thejawnpaul.gptinvestor.homeimpl

import com.thejawnpaul.gptinvestor.homeimpl.model.CompanyRemote
import retrofit2.http.GET

interface HomeService {
  @GET("companies") suspend fun getCompanies(): List<CompanyRemote>
}
