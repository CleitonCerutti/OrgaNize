import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.organize"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.organize"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Carrega a chave de API do arquivo local.properties
            val localProperties = Properties().apply {
                load(project.rootProject.file("local.properties").inputStream())
            }
            val geminiApiKey = localProperties.getProperty("GEMINI_API_KEY", "")
            if (geminiApiKey.isNotEmpty()) {
                // Define a chave de API como um campo de build
                buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
            } else {
                throw GradleException("A chave de API 'GEMINI_API_KEY' não foi encontrada no arquivo local.properties")
            }
        }

        debug {
            // Carrega a chave de API do arquivo local.properties
            val localProperties = Properties().apply {
                load(project.rootProject.file("local.properties").inputStream())
            }
            val geminiApiKey = localProperties.getProperty("GEMINI_API_KEY", "")
            if (geminiApiKey.isNotEmpty()) {
                // Define a chave de API como um campo de build
                buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
            } else {
                throw GradleException("A chave de API 'GEMINI_API_KEY' não foi encontrada no arquivo local.properties")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.common)
    implementation(libs.support.annotations)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Biblioteca do Google Gemini (API de linguagem generativa)
    implementation("com.google.ai.generativelanguage:generativelanguage:0.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3") // Para logging
    implementation("com.google.code.gson:gson:2.10.1") // Para manipulação de JSON

    // Retrofit (opcional, para requisições HTTP)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines (opcional, se estiver usando Kotlin)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ViewModel e LiveData (opcional, para MVVM)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Navigation (opcional, para navegação entre telas)
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
}
