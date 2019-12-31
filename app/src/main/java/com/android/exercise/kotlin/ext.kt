package com.android.exercise.kotlin

import android.content.Context
import android.widget.Toast

/**
 * ext
 * Created by wangzhen on 2019-12-31.
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}