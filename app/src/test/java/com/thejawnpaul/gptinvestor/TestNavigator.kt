package com.thejawnpaul.gptinvestor

import com.thejawnpaul.gptinvestor.navigation.Navigator

class FakeNavigator : Navigator {

  var navigateUpCalled = false
  var destination: String? = null

  override fun navigateUp() {
    navigateUpCalled = true
  }

  override fun navigate(route: String) {
    destination = route
  }
}
