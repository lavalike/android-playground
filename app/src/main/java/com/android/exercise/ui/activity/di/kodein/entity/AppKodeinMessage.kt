package com.android.exercise.ui.activity.di.kodein.entity

data class AppKodeinMessage(val name: String = "Kodein-DI!") : KodeinMessage {
    override fun say(): String = "Hello $name"
}
