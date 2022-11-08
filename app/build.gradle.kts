plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.learn.mydiary"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    ndkVersion = "23.0.7599858"
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.activityKtx)
    implementation(Dependencies.AndroidX.fragmentKtx)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.swipeRefreshLayout)
    implementation(Dependencies.AndroidX.coreKTX)
    implementation(Dependencies.AndroidX.camera)
    implementation(Dependencies.AndroidX.cameraLifecycle)
    implementation(Dependencies.AndroidX.cameraView)
    implementation(Dependencies.AndroidX.lifecycleViewModelKTX)
    implementation(Dependencies.AndroidX.lifecycleLiveDataKTX)
    implementation(Dependencies.AndroidX.navigationFragmentKTX)
    implementation(Dependencies.AndroidX.navigationUiKTX)
    implementation(Dependencies.AndroidX.dataStore)
    implementation(Dependencies.UI.material)
    implementation(Dependencies.UI.coil)
    implementation(Dependencies.UI.shimmer)
    implementation(Dependencies.UI.lottie)
    implementation(Dependencies.Common.gson)
    implementation(Dependencies.Common.playServiceMaps)
    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.retrofitConverterGson)
    implementation(Dependencies.Network.okhttp)
    implementation(Dependencies.Network.okhttpLogging)
    implementation(Dependencies.Paging.paging)
    implementation(Dependencies.Coroutine.core)
    implementation(Dependencies.Coroutine.android)
    implementation(Dependencies.Dagger.hilt)
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt(Dependencies.Dagger.hiltCompiler)
    testImplementation(Dependencies.Testing.junit)
    testImplementation(Dependencies.Testing.mockitoInline)
    testImplementation(Dependencies.Testing.mockitoCore)
    testImplementation(Dependencies.Testing.arch)
    testImplementation(Dependencies.Testing.coroutineTest)
    androidTestImplementation(Dependencies.Testing.junitTest)
    androidTestImplementation(Dependencies.Testing.espressoTest)
}