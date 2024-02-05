package com.android.exercise.base

import androidx.multidex.MultiDexApplication
import com.android.exercise.ui.activity.di.koin.entiry.User
import com.android.exercise.ui.activity.di.koin.service.UserService
import com.android.exercise.ui.activity.di.koin.service.UserServiceImpl
import com.android.exercise.util.AppUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.litepal.LitePal

/**
 * Created by Administrator on 2016/4/12.
 */
class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppUtil.init(this)
        LitePal.initialize(this)

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }

    private val appModule = module {
        factory { User() }
        single<UserService> { UserServiceImpl(get()) }
    }
}