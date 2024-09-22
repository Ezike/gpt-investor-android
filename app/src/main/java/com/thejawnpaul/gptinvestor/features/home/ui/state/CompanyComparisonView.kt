package com.thejawnpaul.gptinvestor.features.home.ui.state

data class CompanyComparisonView(
  val loading: Boolean = false,
  val result: String? = null,
  val error: String? = null,
  val selectedCompany: String? = null,
)
