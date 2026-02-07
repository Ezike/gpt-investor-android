import com.android.build.api.dsl.LibraryExtension

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
}

configure<LibraryExtension> {
  namespace = "com.thejawnpaul.gptinvestor.home_db"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)
}
