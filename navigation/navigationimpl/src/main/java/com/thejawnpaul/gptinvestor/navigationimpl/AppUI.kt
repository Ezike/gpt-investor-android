package com.thejawnpaul.gptinvestor.navigationimpl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thejawnpaul.gptinvestor.navigation.Page
import com.thejawnpaul.gptinvestor.navigation.args
import com.thejawnpaul.gptinvestor.navigationimpl.NavEvent.ToRoute
import com.thejawnpaul.gptinvestor.navigationimpl.NavEvent.Up
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class AppUI
@Inject
constructor(private val navState: NavState, private val pages: Set<@JvmSuppressWildcards Page>) {

  @Composable
  fun Content(startRoute: String = "home", navController: NavHostController) {
    val navPages = remember(pages) { pages.toImmutableList() }
    NavHost(navController = navController, startDestination = startRoute) {
      navPages.forEach { page ->
        composable(
          route = page.route.path,
          arguments =
            page.route.args.map { arg ->
              navArgument(arg) {
                type = NavType.StringType
                nullable = true
              }
            },
        ) { backstackEntry ->
          page.content(backstackEntry.arguments ?: bundleOf())
        }
      }
    }
    LaunchedEffect(navState, navController) {
      navState.navEvents.collect { event ->
        when (event) {
          is ToRoute -> navController.navigate(event.route)
          Up -> navController.navigateUp()
        }
      }
    }
  }
}
