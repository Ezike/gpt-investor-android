import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
}

android {
  val localProperties = Properties()
  localProperties.load(project.rootProject.file("local.properties").reader())
  namespace = "com.thejawnpaul.gptinvestor.remote"
  compileSdk = libs.versions.compileSdk.get().toInt()
  defaultConfig.minSdk = libs.versions.minSdk.get().toInt()
  buildTypes {
    release {
      val baseUrl: String = localProperties.getProperty("BASE_URL") ?: ""
      buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
      val accessToken: String = localProperties.getProperty("ACCESS_TOKEN") ?: ""
      buildConfigField("String", "ACCESS_TOKEN", "\"$accessToken\"")
    }
    debug {
      val baseUrl: String = localProperties.getProperty("BASE_URL") ?: ""
      buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
      val accessToken: String = localProperties.getProperty("ACCESS_TOKEN") ?: ""
      buildConfigField("String", "ACCESS_TOKEN", "\"$accessToken\"")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  buildFeatures.buildConfig = true
}

dependencies {
  implementation(libs.dagger.hilt)
  implementation(libs.retrofit)
  implementation(libs.moshi.converter)
  implementation(libs.okhttp.logger)
  ksp(libs.dagger.hilt.compiler)
}
