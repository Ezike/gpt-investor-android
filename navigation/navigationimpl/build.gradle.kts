import com.android.build.api.dsl.LibraryExtension

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hiltAndroid)
  alias(libs.plugins.compose.compiler)
}

configure<LibraryExtension> {
  namespace = "com.thejawnpaul.gptinvestor.navigationimpl"
  compileSdk = libs.versions.compileSdk.get().toInt()
  defaultConfig.minSdk = libs.versions.minSdk.get().toInt()
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(project(":navigation:navigation"))
  implementation(libs.kotlin.immutable.collections)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.dagger.hilt)
  ksp(libs.dagger.hilt.compiler)
}
