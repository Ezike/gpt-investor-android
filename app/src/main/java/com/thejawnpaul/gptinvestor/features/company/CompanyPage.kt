package com.thejawnpaul.gptinvestor.features.company

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.thejawnpaul.gptinvestor.features.company.ui.CompanyDetailScreen
import com.thejawnpaul.gptinvestor.navigation.Page
import com.thejawnpaul.gptinvestor.navigation.PageContext
import com.thejawnpaul.gptinvestor.navigation.Route

class CompanyPage : Page {

    override val route: Route get() = Route.CompanyDetail

    @Composable
    override fun Content(pageContext: PageContext) {
        val companyViewModel = hiltViewModel<CompanyViewModel>()
        CompanyDetailScreen(
            modifier = Modifier,
            viewModel = companyViewModel,
            onNavigationBtnClick = pageContext.navigator::navigateUp,
            onNewsClick = { url ->
                pageContext.navigator.navigate(
                    route = Route.NewsDetail,
                    arg = url,
                )
            }
        )
    }
}