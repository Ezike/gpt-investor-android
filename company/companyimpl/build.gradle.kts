import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hiltAndroid)
}

android {
  namespace = "com.thejawnpaul.gptinvestor.companyimpl"
  compileSdk = 34
  defaultConfig.minSdk = 24
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  val localProperties =
    Properties().apply { load(project.rootProject.file("local.properties").reader()) }
  buildTypes {
    release {
      val geminiApiKey: String = localProperties.getProperty("GEMINI_API_KEY") ?: ""
      buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
    }
    debug {
      val geminiApiKey: String = localProperties.getProperty("GEMINI_DEBUG_KEY") ?: ""
      buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
    }
  }
  buildFeatures { buildConfig = true }
  kotlinOptions { jvmTarget = "17" }
}

dependencies {
  implementation(project(":theme"))
  implementation(project(":navigation:navigation"))
  implementation(project(":company:company-db"))
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.richtext.compose)
  implementation(libs.richtext.commonmark)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.hilt.navigation)
  implementation(libs.timber)
  implementation(libs.timeAgo)
  implementation(libs.retrofit)
  implementation(libs.dagger.hilt)
  implementation(libs.coil.compose)
  implementation(libs.moshi)
  implementation(libs.gemini)
  implementation(libs.jsoup)
  ksp(libs.dagger.hilt.compiler)
  ksp(libs.moshi.codeGen)
}
