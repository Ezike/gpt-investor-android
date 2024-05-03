package com.thejawnpaul.gptinvestor.features.home

import com.thejawnpaul.gptinvestor.features.home.model.request.AnalystRatingRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.AnalystRatingResponse
import com.thejawnpaul.gptinvestor.features.home.model.request.DefaultSaveResponse
import com.thejawnpaul.gptinvestor.features.home.model.request.DownloadPdfRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.DownloadPdfResponse
import com.thejawnpaul.gptinvestor.features.home.model.request.IndustryRatingRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.SaveComparisonRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.SaveSentimentRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface InvestorService {
    @POST("save-comparison")
    suspend fun saveComparison(@Body request: SaveComparisonRequest): DefaultSaveResponse

    @POST("save-sentiment")
    suspend fun saveSentiment(@Body request: SaveSentimentRequest): DefaultSaveResponse

    @POST("get-analyst-rating")
    suspend fun getAnalystRating(@Body request: AnalystRatingRequest): AnalystRatingResponse

    @POST("save-industry-rating")
    suspend fun saveIndustryRating(@Body request: IndustryRatingRequest): DefaultSaveResponse

    @POST("create-pdf")
    suspend fun createPdf(@Body request: DownloadPdfRequest): DownloadPdfResponse
}
