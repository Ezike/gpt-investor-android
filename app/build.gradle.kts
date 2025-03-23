import java.util.Properties

plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hiltAndroid)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.googleServices)
  alias(libs.plugins.crashlytics)
}

val keystoreProperties = Properties()

keystoreProperties.load(project.rootProject.file("keystore.properties").reader())

android {
  val localProperties = Properties()
  localProperties.load(project.rootProject.file("local.properties").reader())

  namespace = "com.thejawnpaul.gptinvestor"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.thejawnpaul.gptinvestor"
    minSdk = 24
    targetSdk = 35
    versionCode = 2
    versionName = "1.0.1"
    vectorDrawables { useSupportLibrary = true }
  }
  testOptions.unitTests.isIncludeAndroidResources = true
  signingConfigs {
    create("release") {
      keyAlias = keystoreProperties.getProperty("KEY_ALIAS") ?: ""
      keyPassword = keystoreProperties.getProperty("KEY_PASSWORD") ?: ""
      storeFile = file(keystoreProperties.getProperty("STORE_FILE") ?: "")
      storePassword = keystoreProperties.getProperty("KEY_STORE_PASSWORD") ?: ""
    }
  }

  buildTypes {
    release {
      signingConfig = signingConfigs.getByName("release")
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    debug {
      isShrinkResources = false
      isMinifyEnabled = false
      versionNameSuffix = "-dev"
      isDebuggable = true
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions { jvmTarget = "17" }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

ktfmt { googleStyle() }

dependencies {
  implementation(project(":theme"))
  implementation(project(":database"))
  implementation(project(":home:homeimpl"))
  implementation(project(":remote:remote"))
  implementation(project(":company:companyimpl"))
  implementation(project(":navigation:navigationimpl"))
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.dagger.hilt)
  implementation(libs.timber)
  implementation(libs.androidx.navigation.compose)
  ksp(libs.dagger.hilt.compiler)
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.analaytics)
  implementation(libs.firebase.crashlytics)
}
