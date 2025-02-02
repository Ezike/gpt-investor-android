package com.thejawnpaul.gptinvestor.features.company

import com.thejawnpaul.gptinvestor.features.company.ui.NewsDetailScreen
import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.page

internal fun newsPage(navigator: Navigator) =
  page("news?url={url}") {
    NewsDetailScreen(url = arg("url")!!, onNavigationBtnClick = navigator::navigateUp)
  }
