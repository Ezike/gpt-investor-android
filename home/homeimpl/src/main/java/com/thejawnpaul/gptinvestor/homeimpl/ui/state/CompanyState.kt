package com.thejawnpaul.gptinvestor.homeimpl.ui.state

import com.thejawnpaul.gptinvestor.homeimpl.model.CompanyData

data class CompanyState(
  val loading: Boolean = false,
  val companies: List<CompanyData> = emptyList(),
  val error: String? = null,
) {
  val showError = error != null && companies.isEmpty()
}
