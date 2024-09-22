package com.thejawnpaul.gptinvestor.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

interface Navigator {
  fun navigateUp()

  fun navigate(route: Route, arg: String? = null)

  fun getBackstackEntry(route: Route): NavBackStackEntry
}

class AppNavigator(private val navHostController: NavHostController) : Navigator {

  override fun navigateUp() {
    navHostController.navigateUp()
  }

  override fun navigate(route: Route, arg: String?) {
    var destination = route.value
    if (arg != null) {
      destination = destination.replaceAfter("/", arg)
    }
    navHostController.navigate(destination)
  }

  override fun getBackstackEntry(route: Route): NavBackStackEntry =
    navHostController.getBackStackEntry(route.value)
}
