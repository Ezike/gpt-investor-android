package com.thejawnpaul.gptinvestor

import androidx.compose.runtime.Stable
import com.thejawnpaul.gptinvestor.features.company.CompanyPage
import com.thejawnpaul.gptinvestor.features.company.NewsDetailPage
import com.thejawnpaul.gptinvestor.features.home.HomePage
import kotlinx.collections.immutable.persistentMapOf

@Stable
class PageRegistry {

  private val homePage = HomePage()
  private val companyPage = CompanyPage()
  private val newsDetailPage = NewsDetailPage()

  val pages =
    persistentMapOf(
      homePage.route to homePage,
      companyPage.route to companyPage,
      newsDetailPage.route to newsDetailPage,
    )
}
