package com.thejawnpaul.gptinvestor.features.home.ui.state

data class CompanySentimentView(
  val loading: Boolean = false,
  val result: String? = null,
  val error: String? = null,
)
