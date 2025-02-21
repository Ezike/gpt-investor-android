import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.hiltAndroid)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.thejawnpaul.gptinvestor.remotetest"
  compileSdk = 34
  defaultConfig.minSdk = 24
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions { jvmTarget = "17" }
  buildFeatures { buildConfig = true }
}

kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_17 } }

dependencies {
  implementation(libs.dagger.hilt)
  implementation(libs.retrofit)
  implementation(libs.moshi.converter)
  implementation(libs.okhttp.logger)
  ksp(libs.dagger.hilt.compiler)
}
