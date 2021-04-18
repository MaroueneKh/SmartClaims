// Top-level build file where you can add configuration options common to all sub-projects/modules.
ktlint {
    version.set(Versions.ktlint) // "0.34.2"
    debug.set(true)
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    reporters.set(
        setOf(
            org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN,
            org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE
        )
    )
    ignoreFailures.set(true)
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.30.1-alpha")
        classpath("com.google.gms:google-services:4.3.5")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
plugins {
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlintPlugin // "8.2.0"
    id("io.gitlab.arturbosch.detekt") version "1.16.0"
}
allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}