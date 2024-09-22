package com.thejawnpaul.gptinvestor.features.home.model.request

data class SimilarCompanyRequest(
  val ticker: String,
  val historicalData: String,
  val balanceSheet: String,
  val financials: String,
)
