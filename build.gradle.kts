// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("io.objectbox:objectbox-gradle-plugin:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:2.0.21")
    }
}
plugins {
    id("com.android.application").version("8.10.0").apply(false)
    id("com.android.library").version("8.10.0").apply(false)
    id("org.jetbrains.kotlin.android").version("2.0.21").apply(false)
}