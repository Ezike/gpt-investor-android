package com.thejawnpaul.gptinvestor

import com.thejawnpaul.gptinvestor.navigation.Navigator
import com.thejawnpaul.gptinvestor.navigation.Route

interface TestNavigator {
    val navigateUpCalled: Boolean
    val navigateCalled: Boolean
    val destination: Route?
    val navArg: String?
}

class FakeNavigator : Navigator, TestNavigator {

    override var navigateUpCalled = false
    override var navigateCalled = true
    override var destination: Route? = null
    override var navArg: String? = null

    override fun navigateUp() {
        navigateUpCalled = true
    }

    override fun navigate(route: Route, arg: String?) {
        navigateCalled = true
        destination = route
        navArg = arg
    }
}