package com.thejawnpaul.gptinvestor.companyimpl.ui.state

import com.thejawnpaul.gptinvestor.companyimpl.model.SimilarCompanies

data class SimilarCompaniesView(
  val loading: Boolean = false,
  val result: SimilarCompanies? = null,
  val error: String? = null,
)
