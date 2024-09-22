package com.thejawnpaul.gptinvestor.features.company

import androidx.compose.runtime.Composable
import com.thejawnpaul.gptinvestor.features.company.ui.NewsDetailScreen
import com.thejawnpaul.gptinvestor.navigation.Page
import com.thejawnpaul.gptinvestor.navigation.PageContext
import com.thejawnpaul.gptinvestor.navigation.Route

class NewsDetailPage : Page {

  override val route: Route
    get() = Route.NewsDetail

  @Composable
  override fun Content(pageContext: PageContext) {
    pageContext.navArgs?.let { url ->
      NewsDetailScreen(url = url, onNavigationBtnClick = pageContext.navigator::navigateUp)
    }
  }
}
