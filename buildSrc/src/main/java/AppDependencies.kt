import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    //std lib

    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //android ui
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    private val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    //test libs
    val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val material = "com.google.android.material:material:${Versions.material}"
    val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltcompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    val appstartup = "androidx.startup:startup-runtime:${Versions.appstartup}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val datastore = "androidx.datastore:datastore-preferences:1.0.0-alpha08"
    var spinkit = ("com.github.ybq:Android-SpinKit:1.4.0")
    var navigation = ("androidx.navigation:navigation-fragment-ktx:2.3.4")
    var navigation2 = ("androidx.navigation:navigation-ui-ktx:2.3.4")

    // For loading and tinting drawables on older versions of the platform
    var appcompatressources = ("androidx.appcompat:appcompat-resources:1.2.0")
    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(material)
        add(hilt)
        add(appstartup)
        add(timber)
        add(datastore)
        add(spinkit)
        add(navigation)
        add(navigation2)
        add(appcompatressources)

    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
    val kapt = arrayListOf<String>().apply {
        add(hiltcompiler)
    }
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}