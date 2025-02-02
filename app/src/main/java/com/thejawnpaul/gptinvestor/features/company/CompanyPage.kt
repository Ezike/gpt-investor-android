package com.thejawnpaul.gptinvestor.features.company

import androidx.hilt.navigation.compose.hiltViewModel
import com.thejawnpaul.gptinvestor.features.company.ui.CompanyDetailScreen
import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.page

internal fun companyPage(navigator: Navigator) =
  page("company?ticker={ticker}") {
    CompanyDetailScreen(
      viewModel = hiltViewModel<CompanyViewModel>(),
      onNewsClick = navigator::navigate,
      onNavigationBtnClick = navigator::navigateUp,
    )
  }
