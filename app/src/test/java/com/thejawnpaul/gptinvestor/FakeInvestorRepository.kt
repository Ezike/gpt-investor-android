package com.thejawnpaul.gptinvestor

import com.thejawnpaul.gptinvestor.features.home.InvestorRepository
import com.thejawnpaul.gptinvestor.features.home.model.SimilarCompanies
import com.thejawnpaul.gptinvestor.features.home.model.request.CompareCompaniesRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.FinalAnalysisRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.GetPdfRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.SentimentAnalysisRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.SimilarCompanyRequest

class FakeInvestorRepository : InvestorRepository {

  override suspend fun getSimilarCompanies(request: SimilarCompanyRequest): SimilarCompanies =
    SimilarCompanies(codeText = request.ticker, companies = listOf("AMZN", "AAPL", "MSFT"))

  override suspend fun compareCompany(request: CompareCompaniesRequest): String =
    "${request.currentCompanyTicker}-${request.otherCompanyTicker} comparison"

  override suspend fun getSentimentAnalysis(request: SentimentAnalysisRequest): String =
    "${request.ticker} analysis"

  override suspend fun getAnalystRating(ticker: String): String = "$ticker rating"

  override suspend fun getIndustryAnalysis(ticker: String): String = "$ticker analysis"

  override suspend fun getFinalAnalysis(request: FinalAnalysisRequest): String =
    "${request.ticker} analysis"

  override suspend fun downloadAnalysisPdf(request: GetPdfRequest): String = "${request.ticker} pdf"
}
