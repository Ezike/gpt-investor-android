package com.thejawnpaul.gptinvestor.companyimpl.investor.request

import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyFinancialsInfo

data class CompareCompaniesRequest(
  val currentCompany: CompanyFinancialsInfo,
  val otherCompanyTicker: String,
  val currentCompanyTicker: String,
)
