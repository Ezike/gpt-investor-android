package com.thejawnpaul.gptinvestor.companyimpl

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.thejawnpaul.gptinvestor.companyimpl.investor.InvestorRepository
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.CompareCompaniesRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.FinalAnalysisRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.GetPdfRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.SentimentAnalysisRequest
import com.thejawnpaul.gptinvestor.companyimpl.investor.request.SimilarCompanyRequest
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyFinancials
import com.thejawnpaul.gptinvestor.companyimpl.model.CompanyFinancialsInfo
import com.thejawnpaul.gptinvestor.companyimpl.model.NewsInfo
import com.thejawnpaul.gptinvestor.companyimpl.ui.state.CompanyComparisonView
import com.thejawnpaul.gptinvestor.companyimpl.ui.state.CompanyFinancialsView
import com.thejawnpaul.gptinvestor.companyimpl.ui.state.SimilarCompaniesView
import com.thejawnpaul.gptinvestor.companyimpl.ui.state.SingleCompanyView
import com.thejawnpaul.gptinvestor.companyimpl.ui.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel
@Inject
constructor(
  coroutineDispatcher: CoroutineDispatcher,
  private val savedStateHandle: SavedStateHandle,
  private val companyRepository: CompanyRepository,
  private val investorRepository: InvestorRepository,
) : ViewModel(viewModelScope = CoroutineScope(coroutineDispatcher + SupervisorJob())) {

  private val _selectedCompany = MutableStateFlow(SingleCompanyView())
  val selectedCompany
    get() = _selectedCompany

  private val _companyFinancials = MutableStateFlow(CompanyFinancialsView())
  val companyFinancials
    get() = _companyFinancials

  private val _similarCompanies = MutableStateFlow(SimilarCompaniesView())
  val similarCompanies
    get() = _similarCompanies

  private val _companyComparison = MutableStateFlow(CompanyComparisonView())
  val companyComparison
    get() = _companyComparison

  private val _companySentiment = MutableStateFlow(ViewState())
  val companySentiment
    get() = _companySentiment

  private val _analystRating = MutableStateFlow(ViewState())
  val analystRating
    get() = _analystRating

  private val _industryRating = MutableStateFlow(ViewState())
  val industryRating
    get() = _industryRating

  private val _finalAnalysis = MutableStateFlow(ViewState())
  val finalAnalysis
    get() = _finalAnalysis

  private val _downloadPdf = MutableStateFlow(ViewState())
  val downloadPdf
    get() = _downloadPdf

  private val _selectedTab = MutableStateFlow(0)
  val selectedTab
    get() = _selectedTab

  private val companyTicker: String
    get() = savedStateHandle.get<String>("ticker").orEmpty()

  private val sector: String
    get() = savedStateHandle.get<String>("sector").orEmpty()

  private val industry: String
    get() = savedStateHandle.get<String>("industry").orEmpty()

  val companyName: String
    get() = savedStateHandle.get<String>("name").orEmpty()

  init {
    loadCompanyData()
  }

  private fun loadCompanyData() {
    execute(
      state = _companyFinancials,
      action = { companyRepository.getCompanyFinancials(companyTicker) },
      onSuccess = { financials -> CompanyFinancialsView(info = financials.getInfo()) },
    )
    execute(
      state = _selectedCompany,
      action = { companyRepository.getCompany(companyTicker) },
      onSuccess = { copy(company = it) },
    )
  }

  fun getSimilarCompanies() {
    _companyFinancials.value.info?.let {
      val request =
        SimilarCompanyRequest(
          ticker = companyTicker,
          historicalData = it.historicalData,
          balanceSheet = it.balanceSheet,
          financials = it.financials,
        )
      _similarCompanies.update { view -> view.copy(loading = true) }
      execute(
        state = _similarCompanies,
        action = { investorRepository.getSimilarCompanies(request) },
        onSuccess = { companies -> copy(loading = false, result = companies) },
        onFailure = { copy(loading = false, error = "Something went wrong.") },
      )
    }
  }

  fun resetSimilarCompanies() {
    _similarCompanies.update { SimilarCompaniesView() }
    resetCompanyComparison()
    resetGenerativeAIViews()
  }

  fun compareCompanies(ticker: String) {
    _companyFinancials.value.info?.let {
      val request =
        CompareCompaniesRequest(
          currentCompany = it,
          otherCompanyTicker = ticker,
          currentCompanyTicker = companyTicker,
        )
      _companyComparison.update { view ->
        view.copy(loading = true, selectedCompany = ticker, result = null)
      }
      execute(
        state = _companyComparison,
        action = { investorRepository.compareCompany(request) },
        onSuccess = { result -> copy(loading = false, result = result) },
        onFailure = { copy(loading = false, error = "Something went wrong.") },
      )
    }
  }

  fun getSentiment() {
    _companyFinancials.value.info?.let {
      val request = SentimentAnalysisRequest(ticker = companyTicker, news = it.news)
      _companySentiment.update { view -> view.copy(loading = true) }
      execute(
        state = _companySentiment,
        action = { investorRepository.getSentimentAnalysis(request) },
        onSuccess = { result -> copy(loading = false, result = result) },
        onFailure = { copy(loading = false, error = "Something went wrong.") },
      )
    }
  }

  fun getAnalystRating() {
    _analystRating.update { it.copy(loading = true) }
    execute(
      state = _analystRating,
      action = { investorRepository.getAnalystRating(companyTicker) },
      onSuccess = { result -> copy(loading = false, result = result) },
      onFailure = { copy(loading = false, error = "Something went wrong.") },
    )
  }

  fun getIndustryRating() {
    _industryRating.update { it.copy(loading = true) }
    execute(
      state = _industryRating,
      action = {
        investorRepository.getIndustryAnalysis(
          ticker = companyTicker,
          sector = sector,
          industry = industry,
        )
      },
      onSuccess = { result -> copy(loading = false, result = result) },
      onFailure = { copy(loading = false, error = "Something went wrong.") },
    )
  }

  fun getFinalRating() {
    _finalAnalysis.update { it.copy(loading = true) }
    val request =
      FinalAnalysisRequest(
        ticker = companyTicker,
        comparison = _companyComparison.value.result,
        sentiment = _companySentiment.value.result,
        analystRating = "",
        industryRating = "",
      )
    execute(
      state = _finalAnalysis,
      action = { investorRepository.getFinalAnalysis(request) },
      onSuccess = { result -> copy(loading = false, result = result) },
      onFailure = { copy(loading = false, error = "Something went wrong.") },
    )
  }

  fun downloadPdf() {
    val request =
      GetPdfRequest(
        finalRating = _finalAnalysis.value.result ?: "",
        similarCompanies = _similarCompanies.value.result?.codeText ?: "",
        sentiment = _companySentiment.value.result ?: "",
        comparison = _companyComparison.value.result ?: "",
        analystRating = _analystRating.value.result ?: "",
        industryRating = _industryRating.value.result ?: "",
        open = _companyFinancials.value.info?.open ?: "",
        high = _companyFinancials.value.info?.high ?: "",
        low = _companyFinancials.value.info?.low ?: "",
        close = _companyFinancials.value.info?.close ?: "",
        volume = _companyFinancials.value.info?.volume ?: "",
        marketCap = _companyFinancials.value.info?.marketCap ?: "",
        summary = _selectedCompany.value.company?.summary ?: "",
        companyName = _selectedCompany.value.company?.name ?: "",
      )
    _downloadPdf.update { it.copy(loading = true) }
    execute(
      state = _downloadPdf,
      action = { investorRepository.downloadAnalysisPdf(request) },
      onSuccess = { url -> copy(loading = false, result = url) },
      onFailure = { copy(loading = false, error = "Something went wrong.") },
    )
  }

  fun selectTab(tabIndex: Int) {
    _selectedTab.update { tabIndex }
  }

  private fun resetCompanyComparison() {
    _companyComparison.update { CompanyComparisonView() }
  }

  private fun resetGenerativeAIViews() {
    _companySentiment.update { ViewState() }

    _analystRating.update { ViewState() }

    _industryRating.update { ViewState() }

    _finalAnalysis.update { ViewState() }

    _downloadPdf.update { ViewState() }
  }

  private fun <T, R> execute(
    state: MutableStateFlow<T>,
    action: suspend () -> R,
    onSuccess: T.(R) -> T,
    onFailure: T.() -> T = { this },
  ) {
    viewModelScope.launch {
      runCatching { action() }
        .onSuccess { result -> state.update { state -> onSuccess(state, result) } }
        .onFailure { error ->
          if (error is CancellationException) throw error
          Timber.e(error)
          state.update { it.onFailure() }
        }
    }
  }

  private fun CompanyFinancials.getInfo(): CompanyFinancialsInfo {
    return CompanyFinancialsInfo(
      open = open.toCurrency(getCurrencySymbol(currency)),
      low = low.toCurrency(getCurrencySymbol(currency)),
      close = close.toCurrency(getCurrencySymbol(currency)),
      volume = volume.toString(),
      high = high.toCurrency(getCurrencySymbol(currency)),
      marketCap = marketCap.toString(),
      news =
        news.map { companyNews ->
          with(companyNews) {
            NewsInfo(
              title = title.orEmpty(),
              id = id.orEmpty(),
              type = type.orEmpty(),
              relativeDate = TimeAgo.using(time = providerPublishTime?.times(1000) ?: 0),
              publisher = publisher.orEmpty(),
              imageUrl = thumbNail?.resolutions?.first()?.url ?: "",
              link = link.orEmpty(),
            )
          }
        },
      balanceSheet = balanceSheet,
      historicalData = historicalData,
      financials = financials,
    )
  }
}
