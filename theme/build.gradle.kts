import com.android.build.api.dsl.LibraryExtension

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.compose.compiler)
}

configure<LibraryExtension> {
  namespace = "com.thejawnpaul.gptinvestor.theme"
  compileSdk = libs.versions.compileSdk.get().toInt()
  defaultConfig.minSdk = libs.versions.minSdk.get().toInt()
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.material3)
}
