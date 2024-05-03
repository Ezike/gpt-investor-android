package com.thejawnpaul.gptinvestor.navigation

sealed interface NavAction {
    data object NavigateUp : NavAction
    data class ToRoute(
        val route: Route,
        val arg: String? = null,
    ) : NavAction
}