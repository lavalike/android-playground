package com.android.playground.domain

import android.os.Bundle

/**
 * Created by wangzhen on 2017/2/24.
 */
data class Item(
    val name: String,
    val clazz: Class<*>,
    val bundle: Bundle? = null,
) : Generic()
