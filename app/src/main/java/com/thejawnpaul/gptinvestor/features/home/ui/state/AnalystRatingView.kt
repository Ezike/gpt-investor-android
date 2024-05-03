package com.thejawnpaul.gptinvestor.features.home.ui.state

data class AnalystRatingView(
    val loading: Boolean = false,
    val result: String? = null,
    val error: String? = null
)
