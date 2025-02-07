package com.thejawnpaul.gptinvestor.homeimpl

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class HomePageTest {

  @get:Rule val rule = PageTestRule(this)

  private val navigator = FakeNavigator()

  @Test
  fun `check that correct route is registered`() {
    homepage(navigator).test(rule).check { assertThat(route).isEqualTo("home") }
  }

  @Test
  fun `companyClick navigates to Company detail`() {
    homepage(navigator)
      .test(rule)
      .given()
      .whenever { onNodeWithTag("CompanyItem").performClick() }
      .then {
        assertThat(navigator.destination)
          .isEqualTo("company?ticker=AAPL&name=Apple Inc.&sector=tech&industry=tech")
      }
  }
}
