package com.thejawnpaul.gptinvestor.features.company.ui.state

import com.thejawnpaul.gptinvestor.features.company.model.Company

data class AllCompanyView(
  val loading: Boolean = false,
  val companies: List<Company> = emptyList(),
  val error: String? = null,
) {
  val showError = error != null && companies.isEmpty()
}
