import com.android.build.api.dsl.LibraryExtension

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hiltAndroid)
}

configure<LibraryExtension> {
  namespace = "com.thejawnpaul.gptinvestor.homeimpl"
  compileSdk = libs.versions.compileSdk.get().toInt()
  defaultConfig.minSdk = libs.versions.minSdk.get().toInt()
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
  implementation(project(":theme"))
  implementation(project(":navigation:navigation"))
  implementation(project(":home:home-db"))
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.material.icons)
  implementation(libs.androidx.hilt.navigation)
  implementation(libs.timber)
  implementation(libs.retrofit)
  implementation(libs.dagger.hilt)
  implementation(libs.coil.compose)
  implementation(libs.moshi)
  ksp(libs.dagger.hilt.compiler)
  ksp(libs.moshi.codeGen)

  testImplementation(project(":remote:remotetest"))
  kspTest(libs.dagger.hilt.compiler)
  testImplementation(libs.androidx.lifecycle.testing)
  testImplementation(libs.androidx.compose.test)
  testImplementation(libs.androidx.runner)
  testImplementation(libs.truth)
  testImplementation(libs.junit)
  testImplementation(libs.robolectric)
  testImplementation(libs.dagger.hilt.testing)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.junit.ktx)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}
