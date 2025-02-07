package com.thejawnpaul.gptinvestor.homeimpl

import androidx.hilt.navigation.compose.hiltViewModel
import com.thejawnpaul.gptinvestor.homeimpl.ui.HomeScreen
import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.page

fun homepage(navigator: Navigator) =
  page("home") {
    HomeScreen(homeViewModel = hiltViewModel<HomeViewModel>(), onCompanyClick = navigator::navigate)
  }
