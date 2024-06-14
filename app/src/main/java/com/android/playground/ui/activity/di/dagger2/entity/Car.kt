package com.android.playground.ui.activity.di.dagger2.entity

import javax.inject.Inject

class Car @Inject constructor(private val engine: Engine) {
    fun work(): String = "car powered on -> ${engine.work()}"
}