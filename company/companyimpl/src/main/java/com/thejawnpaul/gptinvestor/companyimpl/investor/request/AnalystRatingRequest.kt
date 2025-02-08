package com.thejawnpaul.gptinvestor.companyimpl.investor.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnalystRatingRequest(@field:Json(name = "ticker") val ticker: String)

@JsonClass(generateAdapter = true)
data class AnalystRatingResponse(@field:Json(name = "rating") val rating: String)
