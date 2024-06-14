package com.android.playground.ui.activity.di.koin.service

import com.android.playground.ui.activity.di.koin.entiry.User

class UserServiceImpl(private val user: User) : UserService {
    override fun say(): String = "user say: ${user.message}"
}