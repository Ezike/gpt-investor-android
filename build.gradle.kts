// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.hiltAndroid) apply false
  alias(libs.plugins.ktfmt) apply false
  alias(libs.plugins.googleServices) apply false
  alias(libs.plugins.crashlytics) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.android.library) apply false
}
