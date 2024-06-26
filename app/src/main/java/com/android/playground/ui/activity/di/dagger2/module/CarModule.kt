package com.android.playground.ui.activity.di.dagger2.module

import com.android.playground.ui.activity.di.dagger2.entity.Car
import com.android.playground.ui.activity.di.dagger2.entity.Engine
import dagger.Module
import dagger.Provides

@Module
class CarModule {
    @Provides
    fun provideCar() = Car(provideEngine())

    @Provides
    fun provideEngine() = Engine()
}