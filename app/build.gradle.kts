plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("com.google.gms.google-services")

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
        android.buildFeatures.viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
        implementation(AppDependencies.appLibraries)
        implementation("androidx.annotation:annotation:1.1.0")
        implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
        implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
        implementation("com.squareup.retrofit2:retrofit:2.6.0")
        implementation("com.squareup.retrofit2:converter-gson:2.6.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")
        implementation("androidx.viewpager2:viewpager2:1.0.0")
        implementation("com.google.android.gms:play-services-maps:17.0.0")
        implementation("com.google.android.gms:play-services-location:18.0.0")
        implementation("com.stepstone.stepper:material-stepper:4.3.1")
// CameraX core library using camera2 implementation
        implementation("androidx.camera:camera-camera2:1.0.0-beta07")
// CameraX Lifecycle Library
        implementation("androidx.camera:camera-lifecycle:1.0.0-beta07")
        implementation("androidx.camera:camera-core:1.0.0-beta07")
// CameraX View class
        implementation("androidx.camera:camera-view:1.0.0-alpha14")
        implementation("com.android.support:design:27.0.2")
        implementation("com.github.bumptech.glide:glide:4.12.0")
        implementation("io.coil-kt:coil:1.2.0")
        implementation("com.google.firebase:firebase-bom:27.0.0")
        implementation("com.google.firebase:firebase-storage-ktx:19.2.1")
        implementation("androidx.work:work-runtime-ktx:2.5.0")
        implementation("com.android.support:support-compat:28.0.0")
        implementation("androidx.concurrent:concurrent-futures-ktx:1.1.0")

        kapt("com.google.dagger:dagger-compiler:2.15")
        kapt("androidx.hilt:hilt-compiler:1.0.0-beta01")
        kapt(AppDependencies.kapt)
        kapt("com.android.databinding:compiler:3.2.0-alpha10")
        // test libs
        testImplementation(AppDependencies.testLibraries)
        androidTestImplementation(AppDependencies.androidTestLibraries)
    }
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.navigation:navigation-fragment:2.3.4")
    implementation("androidx.navigation:navigation-ui:2.3.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
}
