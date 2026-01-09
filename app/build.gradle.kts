plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.briefly"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.briefly"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.57.2") // The Core for Hilt Library
    // The Compiler that gives the code for @HiltAndroidApp (using kapt)
    kapt("com.google.dagger:hilt-android-compiler:2.57.2")
    // Integration with Jetpack Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // Kapt: tells to use this tool to read annotations (like @HiltAndroidApp) & write the code for me in background
    // implementation: "I want to use this code in my app"

    // --- NETWORK (Retrofit + Gson) ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // (Optional but good) Logging interceptor to see API calls in Logcat
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    // Firebase
    // Import the Firebase BoM (Bill of Materials)
    // 1. The BOM manages the versions (Use this specific version or newer)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // 2. The Library (NOTICE: No version number at the end!)
    implementation("com.google.firebase:firebase-firestore")

    // Add the dependencies for the Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase library dependencies
    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")
    // (Note: -ktx is now deprecated/included by default in newer versions, so just 'firebase-firestore' is fine)
    implementation("androidx.navigation:navigation-compose:2.7.7")
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