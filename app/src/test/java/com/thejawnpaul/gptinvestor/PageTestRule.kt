package com.thejawnpaul.gptinvestor

import android.app.Application
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.robolectric.Shadows.shadowOf

class PageTestRule(
  private val testClass: Any,
  private val composeTestRule: ComposeContentTestRule = ComposeRule(),
  private val hiltRule: HiltAndroidRule = HiltAndroidRule(testClass),
) : TestRule, ComposeContentTestRule by composeTestRule {

  override fun apply(base: Statement?, description: Description?): Statement =
    RuleChain.outerRule(ActivityRule())
      .around(hiltRule)
      .around(composeTestRule)
      .around(HiltInjectRule())
      .apply(base, description)

  private inner class HiltInjectRule : TestWatcher() {
    override fun starting(description: Description?) {
      super.starting(description)
      hiltRule.inject()
    }
  }

  private class ComposeRule(
    private val composeTestRule: ComposeContentTestRule =
      createAndroidComposeRule<TestHiltActivity>()
  ) : ComposeContentTestRule by composeTestRule {
    override fun setContent(composable: @Composable () -> Unit) {
      composeTestRule.setContent {
        CompositionLocalProvider(LocalLifecycleOwner provides TestLifecycleOwner()) { composable() }
      }
    }
  }

  private class ActivityRule : TestWatcher() {
    override fun starting(description: Description?) {
      super.starting(description)
      val appContext: Application = ApplicationProvider.getApplicationContext()
      val activityInfo =
        ActivityInfo().apply {
          name = TestHiltActivity::class.java.name
          packageName = appContext.packageName
        }
      shadowOf(appContext.packageManager).addOrUpdateActivity(activityInfo)
    }
  }
}
