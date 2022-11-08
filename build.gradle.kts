plugins {
    id ("com.android.application") version(DependenciesVersion.App.GRADLE) apply (false)
    id ("org.jetbrains.kotlin.android") version(DependenciesVersion.App.KOTLIN) apply (false)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${DependenciesVersion.Dagger.HILT}")
    }
}

tasks.register("clean",Delete::class) {
    delete(rootProject.buildDir)
}