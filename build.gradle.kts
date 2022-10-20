plugins {
    id ("com.android.application") version(DependenciesVersion.App.GRADLE) apply (false)
    id ("org.jetbrains.kotlin.android") version(DependenciesVersion.App.KOTLIN) apply (false)
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${DependenciesVersion.Dagger.HILT}")
    }
}

tasks.register("clean",Delete::class) {
    delete(rootProject.buildDir)
}