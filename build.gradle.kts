@file:Suppress("UnstableApiUsage")

import compose_config.composeImplementations

plugins {
    id(Plugins.ANDROID_LIBS)
    kotlin("android")
    id("kotlin-kapt")
    id(Plugins.HILT)
    id("maven-publish")
}

android {
    namespace = "common.hoangdz.lib"
    compileSdk = Configs.TARGET_SDK

    defaultConfig {
        minSdk = Configs.MIN_SUPPORTED_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("proguard-rules.pro")
    }

    libraryVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "hoang_lib_common.aar"
                output.outputFileName = outputFileName
            }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
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
    compileOptions {
        sourceCompatibility = Configs.JAVA_TARGET
        targetCompatibility = Configs.JAVA_TARGET
    }
    kotlinOptions {
        jvmTarget = Configs.JVM_TARGET
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = PluginsVer.COMPOSE_COMPILER
    }
}

dependencies {

    implementation(Deps.KTX_CORE)
    implementation(Deps.APPCOMPAT)
    implementation(Deps.MATERIAL)
    testImplementation(Deps.JUNIT_TEST)
    androidTestImplementation(Deps.JUNIT_TEST_EXT)
    androidTestImplementation(Deps.EPRESSO_CORE)

    //hilt
    implementation(Deps.HILT) { isTransitive = true }
    kapt(Deps.HILT_COMPILER)

    //gson
    implementation(Deps.GSON) { isTransitive = true }

    //eventbus
    implementation(Deps.EVENT_BUS)

    //sdp
    implementation(Deps.SDP) { isTransitive = true }
    implementation(Deps.SSP) { isTransitive = true }

    //Kotlin Android lifecycl
    implementation(Deps.LIFECYCLE_VM) { isTransitive = true }
    implementation(Deps.LIFECYCLE_LIVE_DATA) { isTransitive = true }
    implementation(Deps.LIFECYCLE_RUN_TIME) /*{
        transitive = true
    }*/
    implementation(Deps.LIFECYCLE_VM_STATE) { isTransitive = true }
    kapt(Deps.LIFECYCLE_JAVA_COMMON)
    implementation(Deps.LIFECYCLE_JAVA_COMMON) { isTransitive = true }
    //glide
    implementation(Deps.GLIDE) { isTransitive = true }
    kapt(Deps.GLIDE_COMPILER)

    // lottie animation
    implementation(Deps.LOTTIES)

    //retrofit
    implementation(Deps.RETROFIT)
    implementation(Deps.RETROFIT_GSON_CONVERTER)
    implementation(Deps.RETROFIT_SCALAR_CONVERTER)

    //room
    implementation(Deps.ROOM_RUNTIME)
    implementation(Deps.ROOM_KTX)
    kapt(Deps.ROOM_COMPILER)

    composeImplementations()
}

kapt {
    correctErrorTypes = true
}
