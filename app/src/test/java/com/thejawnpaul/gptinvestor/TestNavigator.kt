package com.thejawnpaul.gptinvestor

import android.content.Context
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
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

    var context: Context? = null

    override fun navigateUp() {
        navigateUpCalled = true
    }

    override fun navigate(route: Route, arg: String?) {
        navigateCalled = true
        destination = route
        navArg = arg
    }

    override fun getBackstackEntry(route: Route): NavBackStackEntry =
        NavBackStackEntry.create(
            context = context,
            destination = NavDestination("test_navigator")
                .apply { this.route = route.value }
        )
}