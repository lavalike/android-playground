package com.android.playground.ui.activity.bitmap.regiondecoder

import android.annotation.SuppressLint
import android.os.Bundle
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.databinding.ActivityBitmapRegionDecoderBinding

/**
 * BitmapRegionDecoderActivity
 * Created by wangzhen on 2022/1/14
 */
class BitmapRegionDecoderActivity : BaseActivity() {
    private lateinit var binding: ActivityBitmapRegionDecoderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBitmapRegionDecoderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        largeImage()
    }

    @SuppressLint("ResourceType")
    private fun largeImage() {
        val stream = resources.openRawResource(R.mipmap.bicycle)
        binding.ivLargeImage.setInputStream(stream)
    }
}