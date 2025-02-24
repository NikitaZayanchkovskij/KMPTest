plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.compilerKspPlugin)
    alias(libs.plugins.daggerHiltPlugin)
    alias(libs.plugins.serializationPlugin)
}

android {
    namespace = "com.nikita_zayanchkovskij.userstestforkmp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nikita_zayanchkovskij.userstestforkmp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    /* Эти зависимости добавил: */
    ksp(libs.androidx.room.compiler)
    ksp(libs.google.dagger.compiler)
    implementation(libs.androidx.room)
    implementation(libs.google.dagger)
    implementation(libs.androidx.dagger.navigation)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.squareup.converter.gson)
    implementation(libs.squareup.retrofit)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.coil.network)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.jetbrains.kotlinx.coroutines.test)
    implementation(libs.google.truth)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}