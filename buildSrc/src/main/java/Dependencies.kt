object Dependencies {
    object AndroidX {
        const val appCompat =
            "androidx.appcompat:appcompat:${DependenciesVersion.AndroidX.APP_COMPAT}"
        const val activityKtx =
            "androidx.activity:activity-ktx:${DependenciesVersion.AndroidX.ACTIVITY_KTX}"
        const val fragmentKtx =
            "androidx.fragment:fragment-ktx:${DependenciesVersion.AndroidX.FRAGMENT_KTX}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${DependenciesVersion.AndroidX.CONSTRAINT_LAYOUT}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${DependenciesVersion.AndroidX.SWIPE_REFRESH_LAYOUT}"
        const val coreKTX = "androidx.core:core-ktx:${DependenciesVersion.AndroidX.CORE_KTX}"
        const val cameraView =
            "androidx.camera:camera-view:${DependenciesVersion.AndroidX.CAMERA}"
        const val camera = "androidx.camera:camera-camera2:${DependenciesVersion.AndroidX.CAMERA}"
        const val cameraLifecycle = "androidx.camera:camera-lifecycle:${DependenciesVersion.AndroidX.CAMERA}"
        const val dataStore = "androidx.datastore:datastore-preferences-core:${DependenciesVersion.AndroidX.DATA_STORE}"
        const val lifecycleViewModelKTX =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependenciesVersion.AndroidX.LIFECYCLE_KTX}"
        const val lifecycleLiveDataKTX =
            "androidx.lifecycle:lifecycle-livedata-ktx:${DependenciesVersion.AndroidX.LIFECYCLE_KTX}"
        const val navigationFragmentKTX = "androidx.navigation:navigation-fragment-ktx:${DependenciesVersion.AndroidX.NAVIGATION_KTX}"
        const val navigationUiKTX = "androidx.navigation:navigation-ui-ktx:${DependenciesVersion.AndroidX.NAVIGATION_KTX}"
    }

    object UI {
        const val material =
            "com.google.android.material:material:${DependenciesVersion.UI.MATERIAL}"
        const val coil = "io.coil-kt:coil:${DependenciesVersion.UI.COIL}"
        const val shimmer = "com.facebook.shimmer:shimmer:${DependenciesVersion.UI.SHIMMER}"
        const val lottie = "com.airbnb.android:lottie:${DependenciesVersion.UI.LOTTIE}"
    }

    object Common {
        const val gson = "com.google.code.gson:gson:${DependenciesVersion.Common.GSON}"
        const val playServiceMaps =
            "com.google.android.gms:play-services-maps:${DependenciesVersion.Common.PLAY_SERVICE_MAPS}"
    }

    object Network {
        const val retrofit =
            "com.squareup.retrofit2:retrofit:${DependenciesVersion.Network.RETROFIT}"
        const val retrofitConverterGson =
            "com.squareup.retrofit2:converter-gson:${DependenciesVersion.Network.RETROFIT}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${DependenciesVersion.Network.OKHTTP}"
        const val okhttpLogging =
            "com.squareup.okhttp3:logging-interceptor:${DependenciesVersion.Network.OKHTTP}"
    }

    object Coroutine {
        const val core =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependenciesVersion.Coroutine.COROUTINES}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependenciesVersion.Coroutine.COROUTINES}"
    }

    object Dagger {
        const val hilt = "com.google.dagger:hilt-android:${DependenciesVersion.Dagger.HILT}"
        const val hiltCompiler =
            "com.google.dagger:hilt-android-compiler:${DependenciesVersion.Dagger.HILT}"
    }

    object Paging {
        const val paging = "androidx.paging:paging-runtime:${DependenciesVersion.Paging.PAGING}"
    }

    object Testing {
        const val junit = "junit:junit:${DependenciesVersion.Testing.JUNIT}"
        const val junitTest = "androidx.test.ext:junit:${DependenciesVersion.Testing.JUNIT_TEST}"
        const val espressoTest =
            "androidx.test.espresso:espresso-core:${DependenciesVersion.Testing.ESPRESSO_CORE_TEST}"
        const val mockitoCore = "org.mockito:mockito-core:${DependenciesVersion.Testing.MOCKITO}"
        const val mockitoInline = "org.mockito:mockito-inline:${DependenciesVersion.Testing.MOCKITO}"
        const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependenciesVersion.Coroutine.COROUTINES}"
        const val arch = "androidx.arch.core:core-testing:${DependenciesVersion.Testing.ARCH}"
    }

}