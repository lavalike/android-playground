package com.android.exercise.ui.activity.memory

import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import kotlinx.android.synthetic.main.activity_large_image.*

/**
 * 大图加载
 * 使用BitmapRegionDecoder加载巨图，细节清晰
 * Created by wangzhen on 2020/7/9.
 */
class LargeImageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_large_image)
        largeImage()
    }

    private fun largeImage() {
        val stream = assets.open("images/wandering_earth.jpg")
        iv_large_image.setInputStream(stream)
    }

    override fun showToolbar(): Boolean {
        return false
    }
}