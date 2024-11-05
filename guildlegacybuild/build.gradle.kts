plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.guildlegacybuild"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.guildlegacybuild"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat.v7)
    implementation(libs.design)
    implementation(libs.constraint.layout)
    implementation(libs.livedata)
    implementation(libs.viewmodel)
    testImplementation(libs.junit)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.android.espresso.core)
}