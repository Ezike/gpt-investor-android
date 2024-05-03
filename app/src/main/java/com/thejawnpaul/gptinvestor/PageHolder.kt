package com.thejawnpaul.gptinvestor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thejawnpaul.gptinvestor.navigation.AppNavigator
import com.thejawnpaul.gptinvestor.navigation.NavAction
import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.PageContext
import com.thejawnpaul.gptinvestor.navigation.Route
import com.thejawnpaul.gptinvestor.navigation.argKey
import com.thejawnpaul.gptinvestor.navigation.withArg

class PageHolder(
    private val navigator: AppNavigator,
    private val pageRegistry: PageRegistry,
    private val navHostController: NavHostController
) {
    @Composable
    fun UI() {
        NavHost(
            navController = navHostController,
            startDestination = Route.Home.value
        ) {
            pageRegistry.pages.forEach { (route, page) ->
                composable(
                    route = route.value,
                    arguments = listOfNotNull(route.argKey?.let { navArgument(it) { NavType.StringType } })
                ) { navBackStackEntry ->
                    val pageContext = remember(navBackStackEntry) {
                        PageContext(
                            navigator = navigator,
                            navArgs = navBackStackEntry.arguments?.getString(route.argKey),
                        )
                    }
                    /**
                     * workaround for [issue](https://issuetracker.google.com/issues/336842920)
                     */
                    CompositionLocalProvider(
                        LocalLifecycleOwner provides navBackStackEntry
                    ) {
                        page.Content(pageContext)
                    }
                }
            }
        }
        LaunchedEffect(navigator, navHostController) {
            navigator.navStackFlow.collect { navAction ->
                when (navAction) {
                    NavAction.NavigateUp -> navHostController.navigateUp()
                    is NavAction.ToRoute -> navHostController.navigate(
                        navAction.route.withArg(navAction.arg)
                    )
                }
            }
        }
    }
}