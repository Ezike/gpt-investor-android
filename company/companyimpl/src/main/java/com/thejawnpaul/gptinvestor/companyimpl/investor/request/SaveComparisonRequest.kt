package com.thejawnpaul.gptinvestor.companyimpl.investor.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaveComparisonRequest(
  @field:Json(name = "main") val mainTicker: String,
  @field:Json(name = "other") val otherTicker: String,
  @field:Json(name = "gemini_text") val geminiText: String,
)

@JsonClass(generateAdapter = true)
data class DefaultSaveResponse(@field:Json(name = "_id") val id: String)
