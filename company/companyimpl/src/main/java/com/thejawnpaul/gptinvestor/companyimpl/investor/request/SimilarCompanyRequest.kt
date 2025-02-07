package com.thejawnpaul.gptinvestor.companyimpl.investor.request

data class SimilarCompanyRequest(
  val ticker: String,
  val historicalData: String,
  val balanceSheet: String,
  val financials: String,
)
