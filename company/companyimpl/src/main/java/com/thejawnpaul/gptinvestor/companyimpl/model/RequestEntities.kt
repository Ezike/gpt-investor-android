package com.thejawnpaul.gptinvestor.companyimpl.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyFinancialsRemote(
  @field:Json(name = "balance_sheet") val balanceSheet: String,
  @field:Json(name = "close") val close: Float,
  @field:Json(name = "open") val open: Float,
  @field:Json(name = "volume") val volume: Long,
  @field:Json(name = "currency") val currency: String,
  @field:Json(name = "financials") val financials: String,
  @field:Json(name = "high") val high: Float,
  @field:Json(name = "historical_data") val historicalData: String,
  @field:Json(name = "low") val low: Float,
  @field:Json(name = "market_cap") val marketCap: Long,
  @field:Json(name = "news") val news: List<CompanyNews>,
)

@JsonClass(generateAdapter = true)
data class CompanyInfoResponse(
  @field:Json(name = "company_name") val name: String,
  @field:Json(name = "ticker") val ticker: String,
  @field:Json(name = "summary") val summary: String,
  @field:Json(name = "logo_url") val logoUrl: String,
)

@JsonClass(generateAdapter = true)
data class CompanyNews(
  @field:Json(name = "link") val link: String?,
  @field:Json(name = "providerPublishTime") val providerPublishTime: Long?,
  @field:Json(name = "publisher") val publisher: String?,
  @field:Json(name = "relatedTickers") val relatedTickers: List<String>?,
  @field:Json(name = "thumbnail") val thumbNail: NewsThumbNail?,
  @field:Json(name = "title") val title: String?,
  @field:Json(name = "type") val type: String?,
  @field:Json(name = "uuid") val id: String?,
)

@JsonClass(generateAdapter = true)
data class NewsThumbNail(
  @field:Json(name = "resolutions") val resolutions: List<NewsResolution> = emptyList()
)

@JsonClass(generateAdapter = true)
data class NewsResolution(
  @field:Json(name = "height") val height: Int,
  @field:Json(name = "tag") val tag: String,
  @field:Json(name = "url") val url: String,
  @field:Json(name = "width") val width: Int,
)

@JsonClass(generateAdapter = true)
data class CompanyFinancialsRequest(
  @field:Json(name = "ticker") val ticker: String,
  @field:Json(name = "years") val years: Int,
)

@JsonClass(generateAdapter = true)
data class CompanyInfoRequest(@field:Json(name = "ticker") val ticker: String)
