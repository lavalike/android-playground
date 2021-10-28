package com.android.exercise.ui.activity.memory

import android.os.Bundle
import com.android.exercise.base.BaseActivity
import com.android.exercise.databinding.ActivityLargeImageBinding

/**
 * 大图加载
 * 使用BitmapRegionDecoder加载巨图，细节清晰
 * Created by wangzhen on 2020/7/9.
 */
class LargeImageActivity : BaseActivity() {
    lateinit var binding: ActivityLargeImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLargeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        largeImage()
    }

    private fun largeImage() {
        val stream = assets.open("images/wandering_earth.jpg")
        binding.ivLargeImage.setInputStream(stream)
    }

    override fun showToolbar(): Boolean {
        return false
    }
}