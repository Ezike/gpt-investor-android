package com.thejawnpaul.gptinvestor

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import com.thejawnpaul.gptinvestor.features.home.HomePage
import com.thejawnpaul.gptinvestor.navigation.Route
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class HomePageTest {

    @get:Rule
    val rule = PageTestRule(this)

    @Test
    fun `check that correct route is registered`() {
        HomePage()
            .test(rule)
            .check { assertThat(route).isEqualTo(Route.Home) }
    }

    @Test
    fun `companyClick navigates to Company detail`() {
        HomePage()
            .test(rule)
            .given()
            .whenever { onNodeWithTag("CompanyItem").performClick() }
            .then {
                assertThat(navArg).isEqualTo("AAPL")
                assertThat(destination).isEqualTo(Route.CompanyDetail)
            }
    }
}