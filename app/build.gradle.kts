import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
	id("com.google.gms.google-services")
}

val customProperties = Properties()
customProperties.load(FileInputStream(rootProject.file("custom.properties")))

android {
    compileSdk = 32

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.trdz.live_note"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = "release"
            keyPassword = customProperties["PASSWORD"] as String
            storeFile = file("/home/miles/keystore.jks")
            storePassword = customProperties["PASSWORD"] as String
        }
    }

    buildTypes {
        release {
            // signingConfig signingConfigs.myRelease
            isMinifyEnabled = false //go true after release
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    dependencies {
        implementation("com.google.firebase:firebase-firestore-ktx:24.1.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.4.1")
        //UI
        implementation(Deps.UI_SnakePager)  // View - SnakePager
        //Image
        implementation(Deps.IMAGE_COIL)     // Image - Coil
        implementation(Deps.IMAGE_SVG)      // Image - Coil svg
        //Injection
        implementation(Deps.KOIN_CORE)
        implementation(Deps.KOIN_ANDROID)   //Koin Features (Scope,ViewModel...)
        implementation(Deps.KOIN_COMPAT)    //Koin Java Compatibility
        //Threader
        implementation(Deps.COROUTINES_CORE)
        implementation(Deps.COROUTINES_ANDROID)
        //Data Server
        implementation(Deps.RETROFIT_CORE)  //Functional Requests
        implementation(Deps.RETROFIT_CONV)  //Functional Requests Converter
        implementation(Deps.RETROFIT_JSON)  //Server Json feature
        //Extension
        implementation(Deps.ANDX_VM)        //MVVM ViewModel addon
        implementation(Deps.ANDX_LD)        //LiveData addon
        implementation(Deps.ANDX_FRAGMENT)  //Fragment addon
        //Basis
        implementation(Deps.ANDX_CORE)
        implementation(Deps.ANDX_LEGACY)    // Legacy
        implementation(Deps.ANDX_APPCOMPAT)
        implementation(Deps.ANDX_CONSTRAINT)
        implementation(Deps.AND_MATERIAL)
    }

}
