import com.android.build.api.dsl.LibraryExtension

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
}

configure<LibraryExtension> {
  namespace = "com.thejawnpaul.gptinvestor.company_db"
  compileSdk = libs.versions.compileSdk.get().toInt()
  defaultConfig.minSdk = libs.versions.minSdk.get().toInt()
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}
dependencies { implementation(libs.androidx.room.ktx) }
