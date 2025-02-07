package com.thejawnpaul.gptinvestor.companyimpl.model

data class CompanyFinancials(
  val open: Float,
  val low: Float,
  val close: Float,
  val volume: Long,
  val high: Float,
  val currency: String,
  val marketCap: Long,
  val news: List<CompanyNews>,
  val historicalData: String,
  val balanceSheet: String,
  val financials: String,
)

data class CompanyFinancialsInfo(
  val open: String,
  val low: String,
  val close: String,
  val volume: String,
  val high: String,
  val marketCap: String,
  val news: List<NewsInfo>,
  val historicalData: String,
  val balanceSheet: String,
  val financials: String,
)

data class NewsInfo(
  val title: String,
  val id: String,
  val type: String,
  val relativeDate: String,
  val publisher: String,
  val imageUrl: String,
  val link: String,
)
