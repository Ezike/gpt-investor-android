package com.thejawnpaul.gptinvestor.features.home

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import com.thejawnpaul.gptinvestor.BuildConfig
import com.thejawnpaul.gptinvestor.features.company.CompanyRepository
import com.thejawnpaul.gptinvestor.features.home.model.SimilarCompanies
import com.thejawnpaul.gptinvestor.features.home.model.request.AnalystRatingRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.CompareCompaniesRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.DownloadPdfRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.FinalAnalysisRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.GetPdfRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.IndustryRatingRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.SaveComparisonRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.SaveSentimentRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.SentimentAnalysisRequest
import com.thejawnpaul.gptinvestor.features.home.model.request.SimilarCompanyRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import timber.log.Timber
import javax.inject.Inject

interface InvestorRepository {
    suspend fun getSimilarCompanies(request: SimilarCompanyRequest): SimilarCompanies
    suspend fun compareCompany(request: CompareCompaniesRequest): String?
    suspend fun getSentimentAnalysis(request: SentimentAnalysisRequest): String?
    suspend fun getAnalystRating(ticker: String): String
    suspend fun getIndustryAnalysis(ticker: String): String?
    suspend fun getFinalAnalysis(request: FinalAnalysisRequest): String?
    suspend fun downloadAnalysisPdf(request: GetPdfRequest): String
}

class InvestorRepositoryImpl @Inject constructor(
    private val investorService: InvestorService,
    private val companyRepository: CompanyRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : InvestorRepository {

    private val model = GenerativeModel(
        modelName = "gemini-1.0-pro",
        // Retrieve API key as an environmental variable defined in a Build Configuration
        // see https://github.com/google/secrets-gradle-plugin for further instructions
        apiKey = BuildConfig.GEMINI_API_KEY,
        generationConfig =
        generationConfig {
            temperature = 0.2f
            topK = 1
            topP = 1f
            maxOutputTokens = 2048
        },
        safetySettings =
        listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
        ),
    )

    override suspend fun getSimilarCompanies(request: SimilarCompanyRequest): SimilarCompanies {
        val news = ""
        val systemPrompt =
            "You are a financial analyst assistant. Analyze the given data for ${request.ticker} " +
                    "and suggest a few comparable companies to consider. Do so in a kotlin-parseable list."
        val additional =
            "Historical price data:\n${request.historicalData}\n\nBalance sheet:\n${request.balanceSheet}\n\nFinancial " +
                    "statements:\n${request.financials}\n\nNews articles:\n${news}\n\n---- \n\n Now, " +
                    "suggest a few comparable companies to consider, in a kotlin-parseable list. Return nothing but the list. " +
                    "Make sure the companies are in the form of their tickers."

        val prompt = systemPrompt + additional
        val response = model.generateContent(prompt = prompt)
        val symbols = getStockSymbols(response.text)
        return SimilarCompanies(codeText = response.text, companies = symbols)
    }

    override suspend fun compareCompany(request: CompareCompaniesRequest): String? {
        val company = companyRepository.getCompanyFinancials(request.otherCompanyTicker)
        val systemPrompt =
            "You are a financial analyst assistant. Compare the data of ${request.currentCompanyTicker} " +
                    "against ${request.currentCompanyTicker} " +
                    "and provide a detailed comparison, like a world-class analyst would. Be measured and discerning. " +
                    "Truly think about the positives and negatives of each company. Be sure of your analysis. " +
                    "You are a skeptical investor."

        val additional =
            "Data for ${request.currentCompanyTicker}:\n\nHistorical price data:\n" +
                    "${request.currentCompany.historicalData}\n\n" +
                    "Balance Sheet:\n${request.currentCompany.balanceSheet}\n\nFinancial Statements:" +
                    "\n${request.currentCompany.financials}\n\n----\n\n" +
                    "Data for ${request.otherCompanyTicker}:\n\nHistorical price data:\n${company.historicalData}\n\n" +
                    "Balance Sheet:\n${company.balanceSheet}\n\nFinancial Statements:\n${company.financials}" +
                    "\n\n----\n\nNow, provide a detailed comparison of ${request.currentCompanyTicker} " +
                    "against ${request.otherCompanyTicker}. " +
                    "Explain your thinking very clearly."
        val prompt = systemPrompt + additional
        val response = model.generateContent(prompt = prompt).text ?: return null
        val saveRequest = SaveComparisonRequest(
            mainTicker = request.currentCompanyTicker,
            otherTicker = request.otherCompanyTicker,
            geminiText = response,
        )
        investorService.saveComparison(saveRequest)
        return response
    }

    override suspend fun getSentimentAnalysis(request: SentimentAnalysisRequest): String? {
        var newsString = ""
        request.news.forEach {
            val text = getArticleText(it.link)?.trim()
            newsString += "\n\n---\n\nDate: ${it.relativeDate}\nTitle: ${it.title}\nText: $text"
        }
        val systemPrompt =
            "You are a sentiment analysis assistant. Analyze the sentiment of the given news articles for ${request.ticker} " +
                    "and provide a summary of the overall sentiment and any notable changes " +
                    "over time. Be measured and discerning. You are a skeptical investor."

        val additionalPrompt =
            "News articles for ${request.ticker}:\n${newsString}\n\n----\n\nProvide a summary of the overall sentiment " +
                    "and any notable changes over time."

        val prompt = systemPrompt + additionalPrompt
        val sentiment = model.generateContent(prompt = prompt).text ?: return null
        val saveRequest = SaveSentimentRequest(ticker = request.ticker, sentiment = sentiment)
        investorService.saveSentiment(saveRequest)
        return sentiment
    }

    override suspend fun getAnalystRating(ticker: String): String {
        val request = AnalystRatingRequest(ticker = ticker)
        return investorService.getAnalystRating(request).rating
    }

    override suspend fun getIndustryAnalysis(ticker: String): String? {
        val company = companyRepository.getCompanyDetails(ticker)
        val systemPrompt =
            "You are an industry analysis assistant. Provide an analysis of the ${company.industry} industry and " +
                    "${company.sector} sector, including trends, growth prospects, regulatory changes, and competitive landscape. " +
                    "Be measured and discerning. Truly think about the positives and negatives of the stock. Be sure of your analysis. " +
                    "You are a skeptical investor."
        val additionalPrompt =
            "Provide an analysis of the ${company.industry} industry and ${company.sector} sector."
        val prompt = systemPrompt + additionalPrompt
        val rating = model.generateContent(prompt = prompt).text ?: return null
        val request = IndustryRatingRequest(
            industry = company.industry,
            sector = company.sector,
            rating = rating,
        )
        investorService.saveIndustryRating(request)
        return rating
    }

    override suspend fun getFinalAnalysis(request: FinalAnalysisRequest): String? {
        val systemPrompt =
            "You are a financial analyst providing a final investment recommendation for ${request.ticker} based on the given " +
                    "data and analyses. Be measured and discerning. Truly think about the positives and " +
                    "negatives of the stock. Be sure of your analysis. You are a skeptical investor."

        val additionalPrompt =
            "Ticker: ${request.ticker}\n\nComparative Analysis:\n${request.comparison}\n\n" +
                    "Sentiment Analysis:\n${request.sentiment}\n\nAnalyst Ratings:\n${request.analystRating}\n\n" +
                    "Industry Analysis:\n${request.industryRating}\n\nBased on the provided data and analyses, " +
                    "please provide a comprehensive investment analysis and recommendation for ${request.ticker}. " +
                    "Consider the company's financial strength, growth prospects, competitive position, and potential risks. " +
                    "Provide a clear and concise recommendation on whether to buy, hold, or sell the stock, along with " +
                    "supporting rationale."

        val prompt = systemPrompt + additionalPrompt
        return model.generateContent(prompt = prompt).text
    }

    override suspend fun downloadAnalysisPdf(request: GetPdfRequest): String {
        val company = companyRepository.getCompany(request.ticker)
        val apiRequest = DownloadPdfRequest(
            name = company.name,
            about = company.summary,
            similarCompanies = request.similarCompanies,
            comparison = request.comparison,
            sentiment = request.sentiment,
            analystRating = request.analystRating,
            industryRating = request.industryRating,
            finalRating = request.finalRating,
            open = request.open,
            high = request.high,
            low = request.low,
            close = request.close,
            volume = request.volume,
            marketCap = request.marketCap,
        )
        return investorService.createPdf(apiRequest).url
    }

    private suspend fun getArticleText(link: String): String? =
        withContext(coroutineDispatcher) {
            try {
                var result = ""
                val document = Jsoup.connect(link).get()
                val paragraphs = document.select("p")
                paragraphs.forEach { paragraph ->
                    val text = paragraph.text().trim()
                    result = "$result $text"
                }
                result
            } catch (e: Exception) {
                Timber.e(e.stackTraceToString())
                null
            }
        }

    private fun getStockSymbols(code: String?): List<String> {
        if (code == null) return emptyList()
        val start = code.indexOf('(') + 1
        val end = code.indexOf(')')
        val sub = code.substring(start, end)
        return sub.splitToSequence(",").map { it.trim() }.toList()
    }
}
