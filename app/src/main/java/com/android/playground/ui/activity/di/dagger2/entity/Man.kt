package com.android.playground.ui.activity.di.dagger2.entity

import javax.inject.Inject

class Man @Inject constructor(private val car: Car) {
    fun work(): String {
        return "man enter the car -> ${car.work()} -> go!"
    }
}