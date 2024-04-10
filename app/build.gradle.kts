plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "site.sayaz.ts3client"
    compileSdk = 34

    defaultConfig {
        applicationId = "site.sayaz.ts3client"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.kotlinx.coroutines.android)
    // implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation(libs.gson)
    implementation(libs.coil)
    implementation(libs.androidx.material3.android)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(files("src/main/java/site/sayaz/ts3client/lib/ts3j.jar"))
    implementation(files("src/main/java/site/sayaz/ts3client/lib/bcprov-jdk15on-1.60.jar"))
    implementation(files("src/main/java/site/sayaz/ts3client/lib/commons-lang-2.6.jar"))
    implementation(files("src/main/java/site/sayaz/ts3client/lib/dnsjava-2.1.8.jar"))
    implementation(files("src/main/java/site/sayaz/ts3client/lib/ini4j-0.5.1.jar"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // https://mvnrepository.com/artifact/com.squareup/kotlinpoet-ksp
    runtimeOnly(libs.kotlinpoet.ksp)
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-android
    runtimeOnly(libs.kotlinx.coroutines.android)


}
kapt {
    correctErrorTypes = true
}