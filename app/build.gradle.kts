import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt") // anotaciones de Glide
    id("com.google.gms.google-services")
}

android {
    namespace = "ar.edu.uade.cocktailapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "ar.edu.uade.cocktailapp"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // Habilitación del Desugaring (para Java 8+)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true // ✅ Asegurarse de habilitar esta opción
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.gson)
    implementation(libs.coil.compose)
    implementation(libs.ads.mobile.sdk)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.androidx.navigation.compose)

    //GLIDE
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")



    // Importa la BoM de Firebase para gestionar las versiones de las bibliotecas
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase Firestore (necesario para FirebaseFirestore + .toObject)
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.0")

    // Coroutines + Firebase (necesario para usar .await())
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Dependencia de desugaring correcta
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3") // Asegurarse de que esta versión sea 2.1.3

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
