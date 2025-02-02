package com.thejawnpaul.gptinvestor

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.core.os.bundleOf
import com.thejawnpaul.gptinvestor.navigation.Page

fun Page.test(composeTestRule: ComposeContentTestRule) =
  PageTestSpec.create(sut = this, composeContentTestRule = composeTestRule)

fun PageTestSpec.check(thenScope: PageTestSpecThenScope.() -> Unit) =
  given().whenever {}.then(thenScope)

class PageTestSpec
private constructor(
  private val sut: Page,
  private val composeContentTestRule: ComposeContentTestRule,
) {

  fun given() = PageTestSpecWhenImpl(sut = sut, composeContentTestRule = composeContentTestRule)

  companion object {
    fun create(sut: Page, composeContentTestRule: ComposeContentTestRule) =
      PageTestSpec(sut = sut, composeContentTestRule = composeContentTestRule)
  }
}

class PageTestSpecWhenImpl(
  private val sut: Page,
  private val composeContentTestRule: ComposeContentTestRule,
) {
  fun whenever(whenScope: PageTestSpecWhenScope.() -> Unit) =
    PageTestSpecThenImpl(sut = sut, whenScope = whenScope, composeTestRule = composeContentTestRule)
}

class PageTestSpecThenImpl(
  private val sut: Page,
  private val whenScope: PageTestSpecWhenScope.() -> Unit,
  private val composeTestRule: ComposeContentTestRule,
) {
  fun then(thenScope: PageTestSpecThenScope.() -> Unit) {
    val thenScopeImpl = ThenScopeImpl(sut)
    composeTestRule.setContent { sut.content(bundleOf()) }
    WhenScopeImpl().whenScope()
    thenScopeImpl.thenScope()
  }

  private inner class ThenScopeImpl(private val sut: Page) : PageTestSpecThenScope {
    override val route: String
      get() = sut.route.path
  }

  private inner class WhenScopeImpl :
    PageTestSpecWhenScope, ComposeContentTestRule by composeTestRule
}

interface PageTestSpecWhenScope : ComposeContentTestRule

interface PageTestSpecThenScope {
  val route: String
}
