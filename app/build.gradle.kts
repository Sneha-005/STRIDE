plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.example.stride"
    compileSdk = 35

    packagingOptions {
        exclude("lib/armeabi-v7a/libtensorflowlite_jni.so")
    }

    defaultConfig {
        applicationId = "com.example.stride"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    signingConfigs {
//        create("release") {
//            storeFile = file("C:/Users/bansa/AndroidStudioProjects/Stride2/keystore.jks")
//            storePassword = "12345678"
//            keyAlias = "Key0"
//            keyPassword = "12345678"
//        }
//        create("Mydebug") {
//            storeFile = file("C:/Users/bansa/AndroidStudioProjects/Stride2/keystore.jks")
//            storePassword = "12345678"
//            keyAlias = "Key0"
//            keyPassword = "12345678"
//        }
//    }
//
//    buildTypes {
//        getByName("release") {
//            signingConfig = signingConfigs.getByName("release")
//        }
//        getByName("debug") {
//            signingConfig = signingConfigs.getByName("Mydebug")
//        }
  //  }

    buildFeatures {
        compose = true
        dataBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.hilt.compose.navigation)
    implementation(libs.hilt)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.common)
    implementation(libs.testng)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.storage)
    kapt(libs.hilt.compiler)
    implementation(libs.material)
    implementation(libs.play.services.fido)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.google.accompanist.flowlayout)
    implementation(libs.ktor.client.core)
    implementation(libs.datastore.preferences)
    implementation(libs.protobuf.javalite)
    implementation(libs.coil)
    implementation(libs.timber)
    implementation(libs.mpandroidchart)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(libs.guava)
    implementation(libs.listenablefuture)
    configurations.all {
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.insert.koin.koin.android)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.play.services.identity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.sdp.android)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.gson)
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")
    implementation(libs.scalar)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.coil.svg)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidx.room.ktx)
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.gpu)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.service)

}
