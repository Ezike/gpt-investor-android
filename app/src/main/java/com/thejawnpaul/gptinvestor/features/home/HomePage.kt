package com.thejawnpaul.gptinvestor.features.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.thejawnpaul.gptinvestor.features.home.ui.HomeScreen
import com.thejawnpaul.gptinvestor.navigation.Page
import com.thejawnpaul.gptinvestor.navigation.PageContext
import com.thejawnpaul.gptinvestor.navigation.Route

class HomePage : Page {

    override val route: Route get() = Route.Home

    @Composable
    override fun Content(pageContext: PageContext) {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(
            homeViewModel = homeViewModel,
            onCompanyClick = { ticker ->
                pageContext.navigator.navigate(
                    route = Route.CompanyDetail,
                    arg = ticker,
                )
            },
        )
    }
}