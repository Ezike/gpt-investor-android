package com.thejawnpaul.gptinvestor.companyimpl.investor

import com.thejawnpaul.gptinvestor.companyimpl.investor.request.AnalystRatingRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.AnalystRatingResponse
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.DefaultSaveResponse
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.DownloadPdfRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.DownloadPdfResponse
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.FinancialsRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.FinancialsResponse
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.IndustryRatingRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.SaveComparisonRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.SaveSentimentRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface InvestorService {

  @POST("company")
  suspend fun getCompanyFinancials(@Body request: FinancialsRequest): FinancialsResponse

  @POST("save-comparison")
  suspend fun saveComparison(@Body request: SaveComparisonRequest): DefaultSaveResponse

  @POST("save-sentiment")
  suspend fun saveSentiment(@Body request: SaveSentimentRequest): DefaultSaveResponse

  @POST("get-analyst-rating")
  suspend fun getAnalystRating(@Body request: AnalystRatingRequest): AnalystRatingResponse

  @POST("save-industry-rating")
  suspend fun saveIndustryRating(@Body request: IndustryRatingRequest): DefaultSaveResponse

  @POST("create-pdf") suspend fun createPdf(@Body request: DownloadPdfRequest): DownloadPdfResponse
}
