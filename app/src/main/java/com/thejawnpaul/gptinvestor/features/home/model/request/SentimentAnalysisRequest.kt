package com.thejawnpaul.gptinvestor.features.home.model.request

import com.thejawnpaul.gptinvestor.features.company.model.NewsInfo

data class SentimentAnalysisRequest(
    val ticker: String,
    val news: List<NewsInfo>
)
