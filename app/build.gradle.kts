plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.organize"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.organize"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val geminiApiKey = System.getenv("GEMINI_API_KEY") ?: ""
        if (geminiApiKey.isEmpty()) {
            logger.warn("AVISO: GEMINI_API_KEY não está configurada nas variáveis de ambiente!")
        }
        buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Configuração correta do androidResources
    androidResources {
        additionalParameters += "--warn-manifest-validation"
        ignoreAssetsPattern += "!.svn:!.git:!.ds_store:!*.scc:.*:<dir>_*:!CVS:!thumbs.db:!picasa.ini:!*~"
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.activity)

    // Material Design
    implementation(libs.material)

    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Networking
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Architecture Components
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
}
