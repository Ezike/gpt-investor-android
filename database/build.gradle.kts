plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.hiltAndroid)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.thejawnpaul.gptinvestor.database"
  compileSdk = 34
  defaultConfig.minSdk = 24
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions { jvmTarget = "17" }
}

dependencies {
  implementation(project(":company:company-db"))
  implementation(project(":home:home-db"))
  implementation(libs.dagger.hilt)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)
  ksp(libs.dagger.hilt.compiler)
}
