package com.thejawnpaul.gptinvestor.companyimpl.investor.request

data class FinalAnalysisRequest(
  val ticker: String,
  val comparison: String? = null,
  val sentiment: String? = null,
  val analystRating: String? = null,
  val industryRating: String? = null,
)
