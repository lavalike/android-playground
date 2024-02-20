package com.android.exercise.ui.activity.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * MainScope
 * Created by wangzhen on 2020/10/15.
 */
class MainScope : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    fun destroy() {
        cancel()
    }
}