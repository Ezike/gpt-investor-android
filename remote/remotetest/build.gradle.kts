import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.hiltAndroid)
  alias(libs.plugins.ksp)
}

configure<LibraryExtension> {
  namespace = "com.thejawnpaul.gptinvestor.remotetest"
  compileSdk = libs.versions.compileSdk.get().toInt()
  defaultConfig.minSdk = libs.versions.minSdk.get().toInt()
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_17 } }

dependencies {
  implementation(libs.dagger.hilt)
  implementation(libs.retrofit)
  implementation(libs.moshi.converter)
  implementation(libs.okhttp.logger)
  ksp(libs.dagger.hilt.compiler)
}
