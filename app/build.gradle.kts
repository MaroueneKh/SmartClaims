import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    // kotlin("dagger.hilt.android.plugin")
    // kotlin("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = "com.marouenekhadhraoui.smartclaims"
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    flavorDimensions(AppConfig.dimension)
    productFlavors {
        create("staging") {
            applicationIdSuffix = ".staging"
            setDimension(AppConfig.dimension)
        }

        create("production") {
            setDimension(AppConfig.dimension)
        }
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }
    dataBinding {
        android.buildFeatures.dataBinding = true
    }
    android {
        dexOptions {
            incremental = false
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kapt {
            generateStubs = true
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
        //app libs
        implementation(AppDependencies.appLibraries)
        implementation("androidx.annotation:annotation:1.1.0")
        // For loading and tinting drawables on older versions of the platform
        implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
        implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
        implementation("com.squareup.retrofit2:retrofit:2.6.0")
        implementation("com.squareup.retrofit2:converter-gson:2.6.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")


        kapt("com.google.dagger:dagger-compiler:2.15")
        kapt("androidx.hilt:hilt-compiler:1.0.0-beta01")
        kapt(AppDependencies.kapt)
        kapt("com.android.databinding:compiler:3.2.0-alpha10")
        //test libs
        testImplementation(AppDependencies.testLibraries)
        androidTestImplementation(AppDependencies.androidTestLibraries)
    }
}