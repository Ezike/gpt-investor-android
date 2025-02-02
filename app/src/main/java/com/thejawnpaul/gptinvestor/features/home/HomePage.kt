package com.thejawnpaul.gptinvestor.features.home

import androidx.hilt.navigation.compose.hiltViewModel
import com.thejawnpaul.gptinvestor.features.home.ui.HomeScreen
import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.page

fun homepage(navigator: Navigator) =
  page("home") {
    HomeScreen(homeViewModel = hiltViewModel<HomeViewModel>(), onCompanyClick = navigator::navigate)
  }
