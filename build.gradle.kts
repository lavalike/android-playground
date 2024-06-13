// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("io.objectbox:objectbox-gradle-plugin:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.20")
    }
}
plugins {
    id("com.android.application").version("8.1.3").apply(false)
    id("com.android.library").version("8.1.3").apply(false)
    id("org.jetbrains.kotlin.android").version("1.8.20").apply(false)
}