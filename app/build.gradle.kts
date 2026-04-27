import org.gradle.kotlin.dsl.implementation as implementation1

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.segundopractico"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.segundopractico"
        minSdk = 30
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation(libs.androidx.ui)
    implementation1(libs.androidx.core.ktx)
    implementation1(libs.androidx.lifecycle.runtime.ktx)
    implementation1(libs.androidx.activity.compose)
    implementation1(platform(libs.androidx.compose.bom))
    implementation1(libs.androidx.compose.ui)
    implementation1(libs.androidx.compose.ui.graphics)
    implementation1(libs.androidx.compose.ui.tooling.preview)
    implementation1(libs.androidx.compose.material3)

    //Librería para hacer peticiones HTTP
    implementation(libs.retrofit)

    //retrofit converter
    implementation(libs.converter.gson)

    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation("io.coil-kt:coil-compose:2.6.0")
}