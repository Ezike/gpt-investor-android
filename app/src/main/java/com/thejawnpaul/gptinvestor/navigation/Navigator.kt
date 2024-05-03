package com.thejawnpaul.gptinvestor.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface Navigator {
    fun navigateUp()
    fun navigate(route: Route, arg: String? = null)
}

class AppNavigator(
    private val coroutineScope: CoroutineScope
) : Navigator {

    private val navStack = MutableSharedFlow<NavAction>()
    val navStackFlow get() = navStack.asSharedFlow()

    override fun navigateUp() {
        coroutineScope.launch {
            navStack.emit(NavAction.NavigateUp)
        }
    }

    override fun navigate(route: Route, arg: String?) {
        coroutineScope.launch {
            navStack.emit(NavAction.ToRoute(route, arg))
        }
    }
}