package com.thejawnpaul.gptinvestor

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.thejawnpaul.gptinvestor.navigation.Page
import com.thejawnpaul.gptinvestor.navigation.PageContext
import com.thejawnpaul.gptinvestor.navigation.Route

fun Page.test(composeTestRule: ComposeContentTestRule) =
    PageTestSpec.create(
        sut = this,
        composeContentTestRule = composeTestRule
    )

fun PageTestSpec.check(thenScope: PageTestSpecThenScope.() -> Unit) =
    given().whenever { }.then(thenScope)

class PageTestSpec private constructor(
    private val sut: Page,
    private val composeContentTestRule: ComposeContentTestRule
) {

    fun given(arg: Any? = null) = PageTestSpecWhenImpl(
        sut = sut,
        arg = arg,
        composeContentTestRule = composeContentTestRule,
    )

    companion object {
        fun create(
            sut: Page,
            composeContentTestRule: ComposeContentTestRule
        ) = PageTestSpec(
            sut = sut,
            composeContentTestRule = composeContentTestRule,
        )
    }
}

class PageTestSpecWhenImpl(
    private val sut: Page,
    private val arg: Any?,
    private val composeContentTestRule: ComposeContentTestRule
) {
    fun whenever(whenScope: PageTestSpecWhenScope.() -> Unit) = PageTestSpecThenImpl(
        sut = sut,
        arg = arg,
        whenScope = whenScope,
        composeTestRule = composeContentTestRule,
    )
}

class PageTestSpecThenImpl(
    private val sut: Page,
    private val arg: Any?,
    private val whenScope: PageTestSpecWhenScope.() -> Unit,
    private val composeTestRule: ComposeContentTestRule,
) {
    fun then(thenScope: PageTestSpecThenScope.() -> Unit) {
        val thenScopeImpl = ThenScopeImpl(sut)
        composeTestRule.setContent {
            sut.Content(
                pageContext = PageContext(
                    navArgs = arg?.toString(),
                    navigator = thenScopeImpl.navigator
                ),
            )
        }
        WhenScopeImpl().whenScope()
        thenScopeImpl.thenScope()
    }

    private inner class ThenScopeImpl(
        private val sut: Page,
        val navigator: FakeNavigator = FakeNavigator(),
    ) : PageTestSpecThenScope, TestNavigator by navigator {
        override val route: Route get() = sut.route
    }

    private inner class WhenScopeImpl :
        PageTestSpecWhenScope,
        ComposeContentTestRule by composeTestRule
}

interface PageTestSpecWhenScope : ComposeContentTestRule
interface PageTestSpecThenScope : TestNavigator {
    val route: Route
}
