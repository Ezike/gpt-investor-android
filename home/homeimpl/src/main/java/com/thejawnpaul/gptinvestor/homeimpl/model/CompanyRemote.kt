package com.thejawnpaul.gptinvestor.homeimpl.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyRemote(
  @field:Json(name = "ticker") val ticker: String,
  @field:Json(name = "sector") val sector: String,
  @field:Json(name = "sector_key") val sectorKey: String,
  @field:Json(name = "industry") val industry: String,
  @field:Json(name = "name") val name: String,
  @field:Json(name = "logo_url") val logoUrl: String,
)
