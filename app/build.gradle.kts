import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin)
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
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thejawnpaul.gptinvestor"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "1.0.1"
        vectorDrawables {
            useSupportLibrary = true
        }
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

            val geminiApiKey: String = localProperties.getProperty("GEMINI_API_KEY") ?: ""
            buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")

            val baseUrl: String = localProperties.getProperty("BASE_URL") ?: ""
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")

            val accessToken: String = localProperties.getProperty("ACCESS_TOKEN") ?: ""
            buildConfigField("String", "ACCESS_TOKEN", "\"$accessToken\"")

            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            val geminiApiKey: String = localProperties.getProperty("GEMINI_DEBUG_KEY") ?: ""
            buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")

            val baseUrl: String = localProperties.getProperty("BASE_URL") ?: ""
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")

            val accessToken: String = localProperties.getProperty("ACCESS_TOKEN") ?: ""
            buildConfigField("String", "ACCESS_TOKEN", "\"$accessToken\"")

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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    enableStrongSkippingMode = true
}

ktfmt {
    googleStyle()
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.dagger.hilt)
    implementation(libs.timber)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.retrofit)
    implementation(libs.moshi.converter)
    implementation(libs.okhttp.logger)
    implementation(libs.coil.compose)
    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.moshi)
    ksp(libs.moshi.codeGen)
    implementation(libs.timeAgo)
    implementation(libs.jsoup)
    implementation(libs.gemini)
    implementation(libs.richtext.compose)
    implementation(libs.richtext.commonmark)
    implementation(platform(libs.firebase.compose.bom))
    implementation(libs.firebase.analaytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.kotlin.immutable.collections)

    kspTest(libs.dagger.hilt.compiler)
    testImplementation(libs.androidx.lifecycle.testing)
    testImplementation(libs.androidx.compose.test)
    testImplementation(libs.androidx.runner)
    testImplementation(libs.truth)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.dagger.hilt.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
