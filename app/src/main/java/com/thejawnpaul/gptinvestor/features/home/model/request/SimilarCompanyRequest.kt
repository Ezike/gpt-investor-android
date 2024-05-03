package com.thejawnpaul.gptinvestor.features.home.model.request

import com.thejawnpaul.gptinvestor.features.company.model.NewsInfo

data class SimilarCompanyRequest(
    val ticker: String,
    val historicalData: String,
    val balanceSheet: String,
    val financials: String,
    val news: List<NewsInfo>
)
