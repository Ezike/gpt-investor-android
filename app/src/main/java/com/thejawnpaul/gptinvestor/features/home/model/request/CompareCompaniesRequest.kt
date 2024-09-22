package com.thejawnpaul.gptinvestor.features.home.model.request

import com.thejawnpaul.gptinvestor.features.company.model.CompanyFinancialsInfo

data class CompareCompaniesRequest(
  val currentCompany: CompanyFinancialsInfo,
  val otherCompanyTicker: String,
  val currentCompanyTicker: String,
)
