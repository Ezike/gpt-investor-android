package com.thejawnpaul.gptinvestor.homeimpl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejawnpaul.gptinvestor.homeimpl.model.CompanyData
import com.thejawnpaul.gptinvestor.homeimpl.model.SectorInput
import com.thejawnpaul.gptinvestor.homeimpl.ui.state.AllSectorView
import com.thejawnpaul.gptinvestor.homeimpl.ui.state.CompanyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(coroutineDispatcher: CoroutineDispatcher, private val homeRepository: HomeRepository) :
  ViewModel(CoroutineScope(coroutineDispatcher + SupervisorJob())) {

  val allSector: StateFlow<AllSectorView>
    field = MutableStateFlow(AllSectorView())

  val allCompanies: StateFlow<CompanyState>
    field = MutableStateFlow(CompanyState())

  init {
    getAllSector()
    getAllCompanies()
  }

  fun selectSector(selected: SectorInput) {
    allSector.update { it.copy(selected = selected) }
    getSectorCompanies(selected)
  }

  fun getAllCompanies() {
    allCompanies.update { it.copy(loading = true, error = null) }
    homeRepository
      .getAllCompanies()
      .onEach(::updateCompanies)
      .catch { throwable ->
        allCompanies.update { it.copy(loading = false, error = "Something went wrong.") }
        Timber.e(throwable)
      }
      .launchIn(viewModelScope)
  }

  private fun getAllSector() {
    viewModelScope.launch {
      runCatching { homeRepository.getAllSector() }
        .onSuccess { sectors -> allSector.update { it.copy(sectors = sectors) } }
        .onFailure { throwable ->
          if (throwable is CancellationException) throw throwable
          Timber.e(throwable)
        }
    }
  }

  private fun getSectorCompanies(sectorInput: SectorInput) {
    val sectorKey =
      when (sectorInput) {
        is SectorInput.CustomSector -> sectorInput.sectorKey
        is SectorInput.AllSector -> null
      }
    viewModelScope.launch {
      runCatching { homeRepository.getCompaniesInSector(sectorKey) }
        .onSuccess(::updateCompanies)
        .onFailure { throwable ->
          if (throwable is CancellationException) throw throwable
          Timber.e(throwable)
        }
    }
  }

  private fun updateCompanies(result: List<CompanyData>) {
    allCompanies.update { view -> view.copy(loading = false, companies = result) }
  }
}
