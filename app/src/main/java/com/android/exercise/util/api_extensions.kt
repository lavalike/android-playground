package com.android.exercise.util

import android.widget.Toast

/**
 * api_extensions.kt
 *
 * @author : wangzhen
 * @date : 2024/2/20/020
 */
fun String.toast() {
    Toast.makeText(AppUtil.getContext(), this@toast, Toast.LENGTH_SHORT).show()
}