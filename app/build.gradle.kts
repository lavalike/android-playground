plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("io.objectbox")
}

android {
    namespace = "com.android.exercise"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.exercise"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        multiDexEnabled = true

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(mapOf("room.schemaLocation" to "$projectDir/schemas"))
            }
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("platform.keystore")
            storePassword = "android"
        }
    }

    buildTypes {
        val config = signingConfigs.getByName("release")
        debug {
            isMinifyEnabled = false
            signingConfig = config
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = config
        }
    }
    lint {
        abortOnError = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    //Jetpack
    implementation("androidx.work:work-runtime:2.7.1")
    implementation("androidx.room:room-runtime:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.draganddrop:draganddrop:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // 三方
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:mockwebserver:4.7.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.litepal.guolindev:core:3.2.1")

    implementation("com.yanzhenjie:andserver:1.0.2")
    implementation("com.jcodecraeer:xrecyclerview:1.6.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.tencent:mmkv-static:1.1.1")
    implementation("com.davemorrissey.labs:subsampling-scale-image-view-androidx:3.10.0")

    // 自研
    implementation("com.wangzhen:LinkTextView:1.0.0")
    implementation("com.wangzhen:GradientLayout:1.0.0")
    implementation("com.wangzhen:ElasticLayout:0.0.3")
    implementation("com.wangzhen:dark-status-bar:1.0.1")
    implementation("com.wangzhen:refresh-layout:0.2.0")
    implementation("com.haibin:calendarview:3.6.4")
    implementation("com.wangzhen:download-client-android:0.0.2")
    implementation("com.github.lavalike:circle-imageview:0.0.1")
    implementation("com.github.lavalike:adapter:0.2.0")
    implementation("com.github.lavalike:permission:0.0.2")
    implementation("com.github.lavalike:commons:0.0.3")

    // player
    //noinspection GradleDependency
    implementation("com.google.android.exoplayer:exoplayer:2.9.6")
    //noinspection GradleDependency
    implementation("com.google.android.exoplayer:exoplayer-core:2.9.6")
    //noinspection GradleDependency
    implementation("com.google.android.exoplayer:exoplayer-dash:2.9.6")
    //noinspection GradleDependency
    implementation("com.google.android.exoplayer:exoplayer-ui:2.9.6")

    implementation("tv.danmaku.ijk.media:ijkplayer-java:0.8.8")
    implementation("tv.danmaku.ijk.media:ijkplayer-armv7a:0.8.8")

    // dagger2
    implementation("com.google.dagger:dagger:2.28.3")
    implementation("com.google.dagger:dagger-android-support:2.28.3")
    kapt("com.google.dagger:dagger-compiler:2.28.3")
    kapt("com.google.dagger:dagger-android-processor:2.28.3")

    // Koin main features for Android
    implementation("io.insert-koin:koin-android:3.3.0")
    // Java Compatibility
    implementation("io.insert-koin:koin-android-compat:3.3.0")
    // Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-workmanager:3.3.0")
    // Navigation Graph
    implementation("io.insert-koin:koin-androidx-navigation:3.3.0")

    // Kodein-DI
    implementation("org.kodein.di:kodein-di:7.15.1")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
}