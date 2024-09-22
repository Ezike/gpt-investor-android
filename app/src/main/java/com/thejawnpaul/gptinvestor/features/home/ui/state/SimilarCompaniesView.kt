package com.thejawnpaul.gptinvestor.features.home.ui.state

import com.thejawnpaul.gptinvestor.features.home.model.SimilarCompanies

data class SimilarCompaniesView(
  val loading: Boolean = false,
  val result: SimilarCompanies? = null,
  val error: String? = null,
)
