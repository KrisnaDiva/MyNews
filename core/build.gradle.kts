plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.krisna.diva.core"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")
        buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY")}\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
apply(from = "../common.gradle")
dependencies {
    // networking
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.squareup.converter.gson)

    // room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // lifecycle
    api(libs.androidx.lifecycle.livedata.ktx)
}