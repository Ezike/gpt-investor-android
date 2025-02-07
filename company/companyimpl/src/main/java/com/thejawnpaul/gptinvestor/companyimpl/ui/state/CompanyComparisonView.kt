package com.thejawnpaul.gptinvestor.companyimpl.ui.state

data class CompanyComparisonView(
  val loading: Boolean = false,
  val result: String? = null,
  val error: String? = null,
  val selectedCompany: String? = null,
)
