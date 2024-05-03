package com.thejawnpaul.gptinvestor.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

interface Page {
    val route: Route

    @Composable
    fun Content(pageContext: PageContext)
}

@Stable
data class PageContext(
    val navArgs: String?,
    val navigator: Navigator
)