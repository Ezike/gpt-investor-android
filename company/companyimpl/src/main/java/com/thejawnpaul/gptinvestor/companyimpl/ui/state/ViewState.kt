package com.thejawnpaul.gptinvestor.companyimpl.ui.state

data class ViewState(
  val loading: Boolean = false,
  val result: String? = null,
  val error: String? = null,
)
