plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.thejawnpaul.gptinvestor.home.db"
  compileSdk = 34
  defaultConfig.minSdk = 24
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions { jvmTarget = "17" }
}

dependencies { implementation(libs.androidx.room.ktx) }
