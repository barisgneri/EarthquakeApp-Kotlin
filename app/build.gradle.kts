plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.compose")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"
}

android {
    namespace = "com.barisguneri.earthquakeapp"
    compileSdk = 36

    kapt {
        correctErrorTypes = true
    }

    defaultConfig {
        applicationId = "com.barisguneri.earthquakeapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }



    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8" // This is now consistent
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// build.gradle.kts

dependencies {
    implementation(platform("androidx.compose:compose-bom:2025.07.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.navigation:navigation-compose:2.9.2")
    implementation("androidx.compose.animation:animation")

    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:3.0.0")
    implementation ("com.squareup.retrofit2:converter-gson:3.0.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // Coil
    implementation ("io.coil-kt:coil-compose:2.7.0")

    //OKHTTP
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")

    // Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.57")
    implementation("androidx.lifecycle:lifecycle-runtime-compose-android:2.9.2")
    implementation("androidx.compose.material3:material3:1.3.2")
    kapt ("com.google.dagger:hilt-android-compiler:2.57")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Other dependencies
    implementation ("androidx.hilt:hilt-work:1.2.0")
    implementation ("androidx.work:work-runtime-ktx:2.9.0")
    implementation ("org.osmdroid:osmdroid-android:6.1.16")

    //Pagging3
    implementation("androidx.paging:paging-runtime-ktx:3.3.6")
    //optional
    implementation("androidx.paging:paging-compose:3.3.6")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.07.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}