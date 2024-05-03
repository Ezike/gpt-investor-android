package com.thejawnpaul.gptinvestor.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejawnpaul.gptinvestor.features.company.CompanyRepository
import com.thejawnpaul.gptinvestor.features.company.model.Company
import com.thejawnpaul.gptinvestor.features.company.model.SectorInput
import com.thejawnpaul.gptinvestor.features.home.ui.state.AllSectorView
import com.thejawnpaul.gptinvestor.features.home.ui.state.CompanyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    coroutineDispatcher: CoroutineDispatcher,
    private val companyRepository: CompanyRepository
) : ViewModel(CoroutineScope(coroutineDispatcher + SupervisorJob())) {

    private val _allSector = MutableStateFlow(AllSectorView())
    val allSector get() = _allSector

    private val _allCompanies = MutableStateFlow(CompanyState())
    val allCompanies get() = _allCompanies

    init {
        getAllSector()
        getAllCompanies()
    }

    fun selectSector(selected: SectorInput) {
        _allSector.update { it.copy(selected = selected) }
        getSectorCompanies(selected)
    }

    fun getAllCompanies() {
        _allCompanies.update { it.copy(loading = true, error = null) }
        companyRepository
            .getAllCompanies()
            .onEach(::updateCompanies)
            .catch { throwable ->
                _allCompanies.update { it.copy(loading = false, error = "Something went wrong.") }
                Timber.e(throwable.message)
            }.launchIn(viewModelScope)
    }

    private fun getAllSector() {
        viewModelScope.launch {
            runCatching {
                companyRepository.getAllSector()
            }.onSuccess { sectors ->
                _allSector.update { it.copy(sectors = sectors) }
            }.onFailure { throwable ->
                if (throwable is CancellationException) throw throwable
                Timber.e(throwable.message)
            }
        }
    }

    private fun getSectorCompanies(sectorInput: SectorInput) {
        val sectorKey = when (sectorInput) {
            is SectorInput.CustomSector -> sectorInput.sectorKey
            is SectorInput.AllSector -> null
        }
        viewModelScope.launch {
            runCatching {
                companyRepository.getCompaniesInSector(sectorKey)
            }.onSuccess(::updateCompanies).onFailure { throwable ->
                if (throwable is CancellationException) throw throwable
                Timber.e(throwable.message)
            }
        }
    }

    private fun updateCompanies(result: List<Company>) {
        _allCompanies.update { view ->
            view.copy(
                loading = false,
                companies = result
            )
        }
    }
}
