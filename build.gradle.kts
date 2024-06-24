buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.5.0")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.2" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1" apply false
}

