package com.android.exercise.ui.activity.di.dagger2.component

import com.android.exercise.ui.activity.di.dagger2.Dagger2Activity
import com.android.exercise.ui.activity.di.dagger2.entity.Car
import com.android.exercise.ui.activity.di.dagger2.entity.Engine
import com.android.exercise.ui.activity.di.dagger2.module.CarModule
import dagger.Component

@Component(modules = [CarModule::class])
interface AppComponent {
    fun getCar(): Car
    fun getEngine(): Engine
    fun inject(activity: Dagger2Activity)
}