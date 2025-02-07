package com.thejawnpaul.gptinvestor.companyimpl

import com.thejawnpaul.gptinvestor.companyimpl.ui.NewsDetailScreen
import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.page

internal fun newsPage(navigator: Navigator) =
  page("news?url={url}") {
    NewsDetailScreen(url = arg("url")!!, onNavigationBtnClick = navigator::navigateUp)
  }
