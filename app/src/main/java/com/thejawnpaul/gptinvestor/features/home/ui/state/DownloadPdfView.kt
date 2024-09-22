package com.thejawnpaul.gptinvestor.features.home.ui.state

data class DownloadPdfView(
  val loading: Boolean = false,
  val result: String? = null,
  val error: String? = null,
)
