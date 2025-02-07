package com.thejawnpaul.gptinvestor.companyimpl.investor.request

import com.thejawnpaul.gptinvestor.companyimpl.model.NewsInfo

data class SentimentAnalysisRequest(val ticker: String, val news: List<NewsInfo>)
