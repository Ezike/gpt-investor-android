package com.thejawnpaul.gptinvestor.companyimpl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.thejawnpaul.gptinvestor.companyimpl.ui.CompanyDetailScreen
import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.page

internal fun companyPage(navigator: Navigator) =
  page("company?ticker={ticker}&name={name}&sector={sector}&industry={industry}") {
    CompanyDetailScreen(
      viewModel = hiltViewModel<CompanyViewModel>(),
      onNewsClick = navigator::navigate,
      onNavigationBtnClick = navigator::navigateUp,
    )
  }
