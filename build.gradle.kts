// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}

gradle
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
gradle
buildscript {
    extra.apply {
        set("gradle_version", "8.1.2") // Versão mais recente do Gradle
        set("kotlin_version", "1.8.0") // Se estiver usando Kotlin
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
    }
}
