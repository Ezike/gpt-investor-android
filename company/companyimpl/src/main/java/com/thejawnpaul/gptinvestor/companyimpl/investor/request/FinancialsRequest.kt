package com.thejawnpaul.gptinvestor.companyimpl.investor.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FinancialsRequest(
  @field:Json(name = "ticker") val ticker: String,
  @field:Json(name = "years") val years: Int,
)

@JsonClass(generateAdapter = true)
data class FinancialsResponse(
  @field:Json(name = "balance_sheet") val balanceSheet: String,
  @field:Json(name = "financials") val financials: String,
  @field:Json(name = "historical_data") val historicalData: String,
)
